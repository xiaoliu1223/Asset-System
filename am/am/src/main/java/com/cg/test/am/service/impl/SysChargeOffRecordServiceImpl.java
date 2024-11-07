package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.*;
import com.cg.test.am.model.*;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysChargeOffRecordService;
import com.cg.test.am.utils.AppletUtils;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysChargeOffRecordResp;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysChargeOffRecordServiceImpl implements SysChargeOffRecordService {

    @Resource
    SysChargeOffRecordMapper sysChargeOffRecordMapper;

    @Resource
    SysAssetMapper sysAssetMapper;

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysAuditLogMapper sysAuditLogMapper;

    @Resource
    SysAssetTypeMapper sysAssetTypeMapper;

    @Resource
    SysPostMapper sysPostMapper;

    @Resource
    HttpServletRequest request;

    @Resource
    SysMessageMapper sysMessageMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public  synchronized void save(SysChargeOffRecordReq req){

        try{
            //根据用户id查询姓名
            SysUser sysUserInfo = sysUserMapper.selectById(req.getUserId());

            //判断所属部门是否是综合管理部并且是资产管理员
            if(!sysUserInfo.getPostId().equals(ParamsConstant.POST_GROUP_ASSET_MANAGER)){
                throw new ChorBizException(AmErrorCode.NO_AUTHORIZATION);
            }

            //根据资产库存ID查询
            SysAsset sysAsset = sysAssetMapper.selectById(req.getAssetId());

            if (null == sysAsset) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            //查询资产类型
            SysAssetType sysAssetType = sysAssetTypeMapper.selectById(sysAsset.getAssetType());
            if(sysAssetType.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)){
                throw new ChorBizException(AmErrorCode.ASSET_TYPE_SCRAP_ERROR);
            }

            if(!sysAsset.getInventoryStatus().equals(ParamsConstant.ASSET_INVENTORY_STATUS_IN)){
                throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
            }

            if (sysAsset.getAssetNum() - sysAsset.getOutNum() - sysAsset.getChargeOffNum() - req.getNum() < 0) {
                throw new ChorBizException(AmErrorCode.NOT_ENOUGH);
            }

            // 查询是否有提交的核销记录
            List<Integer> statusList = new ArrayList<>();
            statusList.add(ParamsConstant.AUDIT_STATUS_DEFAULT);
            statusList.add(ParamsConstant.AUDIT_STATUS_APPROVE);
            statusList.add(ParamsConstant.STATUS_FOR_HAS_SCRAPED);
            int count = sysChargeOffRecordMapper.selectCount(new QueryWrapper<SysChargeOffRecord>().eq("asset_code",sysAsset.getAssetCode()).in("status", statusList));
            if(count>0){
                throw new ChorBizException(AmErrorCode.NO_INSERT_CHARGE_OFF);
            }
            SysChargeOffRecord sysChargeOffRecord = new SysChargeOffRecord();
            CopyUtils.copyProperties(sysAsset, sysChargeOffRecord);
            int random = (int)(Math.random()*900)+100;
            Long time = System.currentTimeMillis();
            sysChargeOffRecord.setId(null);
            sysChargeOffRecord.setJobNo(time+String.valueOf(random));
            sysChargeOffRecord.setUserId(req.getUserId());
            sysChargeOffRecord.setUsername(sysUserInfo.getUsername());
            sysChargeOffRecord.setNum(req.getNum());
            sysChargeOffRecord.setDescription(req.getDescription());


            // 提交给综合部经理
            SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("post_id", sysUserInfo.getSuperiorPostId()));
            sysChargeOffRecord.setFlowPath(sysUser.getId());
            sysChargeOffRecord.setStatus(ParamsConstant.AUDIT_STATUS_DEFAULT);
            sysChargeOffRecord.setCreateTime(System.currentTimeMillis());
            sysChargeOffRecord.setUpdateTime(null);
            sysChargeOffRecordMapper.insertSelective(sysChargeOffRecord);

            String accessToken = AppletUtils.getAccessToken();

        }catch (ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public Map<String,Object> list(SysChargeOffRecordListReq req){
        try{

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));

            Map<String, Object> map = new HashMap<String, Object>();

            // 核销列表只有综合管理部可以看到
            if (!sysUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)) {
                map.put("total_record", 0);
                map.put("data", new ArrayList<>());
                return map;
            }
            req.setDepartmentIds(req.getDepartmentId());
            //req.setDepartmentIds(null);
            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysChargeOffRecordMapper.count(req);
            List<SysChargeOffRecordResp> list = sysChargeOffRecordMapper.list(req, offset, limit);
            map.put("total_record", count);
            map.put("data", list);
            return map;

        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public  Map<String,Object> auditList(AuditListReq req){
        try{
            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysChargeOffRecordMapper.auditCount(req);
            List<SysChargeOffRecordResp> list = sysChargeOffRecordMapper.auditList(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;

        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public SysChargeOffRecordResp find(Long id){
        try{
            //根据id查询
            SysChargeOffRecordResp sysChargeOffRecordResp = sysChargeOffRecordMapper.getById(id);
            if(sysChargeOffRecordResp==null){
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            List<SysAuditLog> sysAuditLogList = new ArrayList<>();

            // 将申请人信息写入
            SysAuditLog sysAuditLog = new SysAuditLog();
            sysAuditLog.setAuditName(sysChargeOffRecordResp.getUsername());  // 核销申请人姓名
            sysAuditLog.setAuditTime(sysChargeOffRecordResp.getCreateTime()); // 核销申请时间
            sysAuditLogList.add(sysAuditLog);

            //根据id查询审批流程
            List<SysAuditLog> sysAuditLogs = sysAuditLogMapper.selectList(new QueryWrapper<SysAuditLog>().eq("related_id", id).eq("type", ParamsConstant.AUDIT_TYPE_FOR_CHARGE_OFF).orderByAsc("id"));
            sysAuditLogList.addAll(sysAuditLogs);
            sysChargeOffRecordResp.setSysAuditLogs(sysAuditLogList);
            return sysChargeOffRecordResp;

        }catch (ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void recall(Long id){
        try{

            //查询下是否存在
            SysChargeOffRecordResp info = sysChargeOffRecordMapper.getById(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }else if(!info.getStatus().equals(ParamsConstant.AUDIT_STATUS_DEFAULT)){
                throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
            }

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));

            // 判断是撤销人员是否是资产核销申请人
            if (!sysUserInfo.getId().equals(info.getUserId())) {
                throw new ChorBizException(AmErrorCode.AUTHORIZATION_ERROR);
            }

            SysChargeOffRecord record = new SysChargeOffRecord();
            record.setId(id);
            record.setStatus(ParamsConstant.AUDIT_STATUS_BACKOUT);
            record.setFlowPath(ParamsConstant.AUDIT_STATUS_DEFAULT);
            record.setUpdateTime(System.currentTimeMillis());
            sysChargeOffRecordMapper.updateByPrimaryKeySelective(record);

        }catch (ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException();
        }
    }

//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public void cancel(Long id){
//        try{
//
//            //查询下是否存在
//            SysChargeOffRecordResp info = sysChargeOffRecordMapper.getById(id);
//            if (null == info) {
//                throw new ChorBizException(AmErrorCode.NULL_FOUND);
//            }else if(!info.getStatus().equals(ParamsConstant.AUDIT_STATUS_APPROVE)){
//                throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
//            }
//            //将核销记录里状态设置为已核销
//            SysChargeOffRecord record = new SysChargeOffRecord();
//            record.setId(id);
//            record.setStatus(ParamsConstant.STATUS_FOR_HAS_SCRAPED);
//            record.setUpdateTime(System.currentTimeMillis());
//            sysChargeOffRecordMapper.updateByPrimaryKeySelective(record);
//
//            //将库存改为已核销
//            sysAssetMapper.scrap(info.getAssetCode(),ParamsConstant.ASSET_INVENTORY_STATUS_SCRAP,info.getNum(),ParamsConstant.ASSET_STATUS_OFF,System.currentTimeMillis());
//
//        }catch (ChorBizException e){
//            throw e;
//        }catch (Exception e){
//            throw new ChorBizException();
//        }
//    }

    @Override
    public void audit(SysAuditLogReq req, Long id) {

        try {

            SysChargeOffRecord sysChargeOffRecordInfo = sysChargeOffRecordMapper.selectOne(new QueryWrapper<SysChargeOffRecord>()
                    .eq("id", id).and(query-> query.eq("status", ParamsConstant.AUDIT_STATUS_ING).or().eq("status", ParamsConstant.AUDIT_STATUS_DEFAULT)));
            if (null == sysChargeOffRecordInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            SysUser auditUserInfo = sysUserMapper.selectById(req.getAuditBy()); // 当前审核用户
            SysUser superUser = superUser(auditUserInfo); // 上级用户

            // 审核日志
            long currentTimeMillis = System.currentTimeMillis();
            SysAuditLog sysAuditLog = new SysAuditLog();
            CopyUtils.copyProperties(req, sysAuditLog);
            sysAuditLog.setAuditTime(currentTimeMillis);
            sysAuditLog.setRelatedId(id);
            sysAuditLog.setType(ParamsConstant.AUDIT_TYPE_FOR_CHARGE_OFF);
            sysAuditLog.setAuditName(auditUserInfo.getUsername());
            sysAuditLogMapper.insertSelective(sysAuditLog);

            // 消息审核状态变更为已审核
            sysMessageMapper.update(null, new UpdateWrapper<SysMessage>()
                    .set("audit_status", ParamsConstant.AUDIT_FLOWPAHT_FINISH)
                    .eq("type", ParamsConstant.AUDIT_TYPE_FOR_CHARGE_OFF)
                    .eq("related_id", id)
                    .eq("to_user", req.getAuditBy()));

            // 审核结果驳回，则终止，不往上提交
            if (req.getAuditStatus().equals(ParamsConstant.AUDIT_STATUS_REJECT)) {

                sysChargeOffRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                sysChargeOffRecordInfo.setStatus(req.getAuditStatus());
                sysChargeOffRecordMapper.updateById(sysChargeOffRecordInfo);
                return;
            }

//            SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(sysChargeOffRecordInfo.getAssetType());
//            if (sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)
//                    || sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_OFFICE)
//                    || sysAssetTypeInfo.getId().equals(ParamsConstant.ASSET_TYPE_OFFICE)
//                    || sysAssetTypeInfo.getId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)) {
//
//                // 审批直至综合管理部分管领导
//                if (ParamsConstant.POST_TYPE_MANAGER_LEADER.contains(auditUserInfo.getPostId())) {
//                    sysChargeOffRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_APPROVE);
//                    sysChargeOffRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
//                } else {
//                    sysChargeOffRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_ING);
//                    sysChargeOffRecordInfo.setFlowPath(superUser.getId());
//                }
//
//            } else {

                // 审批直至总经理
                if (auditUserInfo.getPostId().equals(ParamsConstant.POST_TYPE_GENERAL_MANAGER)) {
                    sysChargeOffRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_APPROVE);
                    sysChargeOffRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                } else {
                    sysChargeOffRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_ING);
                    sysChargeOffRecordInfo.setFlowPath(superUser.getId());
                }
//            }


            sysChargeOffRecordMapper.updateById(sysChargeOffRecordInfo);

            // todo 消息推送
            if (!sysChargeOffRecordInfo.getFlowPath().equals(ParamsConstant.AUDIT_FLOWPAHT_FINISH)) {
                String accessToken = AppletUtils.getAccessToken();
            }

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirm(SysChargeOffRecordConfirmReq req, Long id) {

        try{

            if (null == req.getScrapAmount()) {
                throw new ChorBizException(AmErrorCode.AMOUNT_ERROR);
            }
//            String strAmount = req.getScrapAmount().toString();
//            String regex = "^[+-]?\\\\d+(\\\\.\\\\d+)?$";
//            boolean matches = strAmount.matches(regex);
//            if (!matches) {
//                throw new ChorBizException(AmErrorCode.AMOUNT_ERROR);
//            }

            //查询下是否存在
            SysChargeOffRecordResp info = sysChargeOffRecordMapper.getById(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }else if(!info.getStatus().equals(ParamsConstant.AUDIT_STATUS_APPROVE)){
                throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
            }

            //将核销记录里状态设置为已核销
            SysChargeOffRecord record = new SysChargeOffRecord();
            record.setPinAmount(req.getScrapAmount());
            record.setId(id);
            record.setStatus(ParamsConstant.STATUS_FOR_HAS_SCRAPED);
            record.setUpdateTime(System.currentTimeMillis());
            sysChargeOffRecordMapper.updateByPrimaryKeySelective(record);

            //将库存改为已核销
            sysAssetMapper.update(null,new UpdateWrapper<SysAsset>()
                    .set("inventory_status",ParamsConstant.ASSET_INVENTORY_STATUS_SCRAP)
                    .set("charge_off_num",info.getNum())
                    .set("asset_status",ParamsConstant.ASSET_STATUS_OFF)
                    .set("update_time",System.currentTimeMillis())
                    .set("charge_off_money",req.getScrapAmount())
                    .eq("asset_code",info.getAssetCode()));

        }catch (ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException();
        }
    }

    @Override
    public List<FlowPathResp> auditFlowList(Long id) {

        try {

            // 获取核销资产基本信息
            SysChargeOffRecord sysChargeOffRecordInfo = sysChargeOffRecordMapper.selectById(id);
            if (null ==  sysChargeOffRecordInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            // 获取资产类别
//            SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(sysChargeOffRecordInfo.getAssetType());

            List<FlowPathResp> flowPathRespList = new ArrayList<>();
            FlowPathResp scrapUser = new FlowPathResp();
            scrapUser.setUserId(sysChargeOffRecordInfo.getUserId());
            scrapUser.setUsername(sysChargeOffRecordInfo.getUsername());
            scrapUser.setPostName("申请人");
            flowPathRespList.add(scrapUser); // 核销申请人


            // 获取综合部资产管理员信息
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                    .eq("department_id", ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT).eq("post_id", ParamsConstant.POST_GROUP_ASSET_MANAGER).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            SysUser superUserInfo = superUser(sysUserInfo);

            boolean flag = true;
            while (flag) {

                FlowPathResp flowPathResp = getFlowPathResp(superUserInfo, id);
                if (superUserInfo.getPostId().equals(ParamsConstant.POST_TYPE_GENERAL_MANAGER)) {
                    flag = false;
                }
                superUserInfo = superUser(superUserInfo);
                flowPathRespList.add(flowPathResp);
            }

            // 根据资产类别选择核销流程
//            if (sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_FIXED)) {
//
//                // 总经理
//                SysUser managerLeader = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
//                        .eq("department_id", 1).eq("post_id", ParamsConstant.POST_TYPE_GENERAL_MANAGER).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
//                FlowPathResp flowPathResp = getFlowPathResp(managerLeader, id);
//                flowPathRespList.add(flowPathResp);
//            }

            return flowPathRespList;
        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchAudit(SysAuditLogBatchReq req, Integer auditBy) {

        try {

            for (Long id: req.getIdList()) {
                SysChargeOffRecord sysChargeOffRecordInfo = sysChargeOffRecordMapper.selectOne(new QueryWrapper<SysChargeOffRecord>()
                        .eq("id", id).and(query-> query.eq("status", ParamsConstant.AUDIT_STATUS_ING).or().eq("status", ParamsConstant.AUDIT_STATUS_DEFAULT)));
                if (null == sysChargeOffRecordInfo) {
                    throw new ChorBizException(AmErrorCode.NULL_FOUND);
                }

                SysUser auditUserInfo = sysUserMapper.selectById(auditBy); // 当前审核用户
                SysUser superUser = superUser(auditUserInfo); // 上级用户

                // 审核日志
                long currentTimeMillis = System.currentTimeMillis();
                SysAuditLog sysAuditLog = new SysAuditLog();
                CopyUtils.copyProperties(req, sysAuditLog);
                sysAuditLog.setAuditTime(currentTimeMillis);
                sysAuditLog.setRelatedId(id);
                sysAuditLog.setType(ParamsConstant.AUDIT_TYPE_FOR_CHARGE_OFF);
                sysAuditLog.setAuditName(auditUserInfo.getUsername());
                sysAuditLog.setAuditBy(auditBy);
                sysAuditLogMapper.insertSelective(sysAuditLog);

                // 消息审核状态变更为已审核
                sysMessageMapper.update(null, new UpdateWrapper<SysMessage>()
                        .set("audit_status", ParamsConstant.AUDIT_FLOWPAHT_FINISH)
                        .eq("type", ParamsConstant.AUDIT_TYPE_FOR_CHARGE_OFF)
                        .eq("related_id", id)
                        .eq("to_user", auditBy));

                // 审核结果驳回，则终止，不往上提交
                if (req.getAuditStatus().equals(ParamsConstant.AUDIT_STATUS_REJECT)) {

                    sysChargeOffRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                    sysChargeOffRecordInfo.setStatus(req.getAuditStatus());
                    sysChargeOffRecordMapper.updateById(sysChargeOffRecordInfo);
                    return;
                }

                // 审批直至总经理
                if (auditUserInfo.getPostId().equals(ParamsConstant.POST_TYPE_GENERAL_MANAGER)) {
                    sysChargeOffRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_APPROVE);
                    sysChargeOffRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                } else {
                    sysChargeOffRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_ING);
                    sysChargeOffRecordInfo.setFlowPath(superUser.getId());
                }

                sysChargeOffRecordMapper.updateById(sysChargeOffRecordInfo);

                // todo 消息推送
                if (!sysChargeOffRecordInfo.getFlowPath().equals(ParamsConstant.AUDIT_FLOWPAHT_FINISH)) {
                    String accessToken = AppletUtils.getAccessToken();
                }
            }
        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }


    /**
     * 寻找当前用户上级
     * @param currentUserInfo 当前用户
     * @return
     */
    public SysUser superUser(SysUser currentUserInfo) {

        SysUser sysUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("post_id", currentUserInfo.getSuperiorPostId()));

        if (null == sysUser) {
            return currentUserInfo;
        }
        return sysUser;
    }

    /**
     * 取得 flowPath
     * @param currentUser
     * @param relatedId
     * @return
     */
    public FlowPathResp getFlowPathResp(SysUser currentUser, Long relatedId) {

        FlowPathResp flowPathResp = new FlowPathResp();
        flowPathResp.setUserId(currentUser.getId());
        flowPathResp.setUsername(currentUser.getUsername());
//        SysPost assetUserPost = sysPostMapper.selectById(currentUser.getPostId());
//        flowPathResp.setPostName(assetUserPost.getName());
        flowPathResp.setPostName("审批人");

        // 查看审批状态
        SysAuditLog sysAuditLog = sysAuditLogMapper.selectOne(new QueryWrapper<SysAuditLog>()
                .eq("type", ParamsConstant.AUDIT_TYPE_FOR_CHARGE_OFF).eq("related_id", relatedId).eq("audit_by", currentUser.getId()));

        if (null != sysAuditLog) {
            flowPathResp.setAuditStatus(sysAuditLog.getAuditStatus());
            flowPathResp.setReason(sysAuditLog.getReason());
        }

        return flowPathResp;
    }
}
