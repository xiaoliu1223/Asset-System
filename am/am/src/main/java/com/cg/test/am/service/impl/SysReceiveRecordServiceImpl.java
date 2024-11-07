package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.*;
import com.cg.test.am.model.*;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysReceiveRecordService;
import com.cg.test.am.utils.AppletUtils;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysReceiveRecordListResp;
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
public class SysReceiveRecordServiceImpl implements SysReceiveRecordService {

    @Resource
    SysReceiveRecordMapper sysReceiveRecordMapper;
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    SysAuditLogMapper sysAuditLogMapper;
    @Resource
    SysAssetMapper sysAssetMapper;

    @Resource
    SysAssetTypeMapper sysAssetTypeMapper;

    @Resource
    SysPostMapper sysPostMapper;

    @Resource
    HttpServletRequest request;

    @Resource
    SysMessageMapper sysMessageMapper;

    @Override
    public Map<String,Object> list(SysReceiveRecordListReq req){
        try{

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            String departmentIds = claims.get("fillArgs").toString();
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));

            if(req.getDepartmentId() == null){
                if (sysUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)) {
                    req.setDepartmentIds(null);     // 综合部的可以查看所有部门的资产信息
                } else {
                    req.setDepartmentIds(departmentIds);
                }
            }else{
                req.setDepartmentIds(req.getDepartmentId());
            }

            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysReceiveRecordMapper.count(req);
            List<SysReceiveRecordListResp> list = sysReceiveRecordMapper.list(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public Map<String,Object> auditList(AuditListReq req){
        try{
            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysReceiveRecordMapper.auditCount(req);
            List<SysReceiveRecordListResp> list = sysReceiveRecordMapper.auditlist(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public  void save(SysReceiveRecordReq req){
        try{

            // 判断领用数量是否为0，或者小数
            if (req.getNum() <= 0 || null == req.getNum()) {
                throw new ChorBizException(AmErrorCode.NUM_ERROR);
            }

            SysUser sysUserInfo = sysUserMapper.selectById(req.getUserId());

            // 查看是否是锁定状态
            SysAsset sysAssetInfo = sysAssetMapper.selectOne(new QueryWrapper<SysAsset>()
                    .eq("asset_code", req.getAssetCode()));
            if (null == sysAssetInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            if (sysAssetInfo.getAssetNum() - sysAssetInfo.getOutNum() - sysAssetInfo.getChargeOffNum() - req.getNum() < 0) {
                throw new ChorBizException(AmErrorCode.NOT_ENOUGH);
            }

            if (sysAssetInfo.getIsLocked().equals(ParamsConstant.ASSET_LOCKED_STATUS_ON)) {
                throw new ChorBizException(AmErrorCode.BE_LOCKED);
            }

            if (!sysAssetInfo.getDepartmentId().equals(sysUserInfo.getDepartmentId().intValue())) {
                throw new ChorBizException(AmErrorCode.ASSET_NOT_BELONG);
            }

            int random = (int)(Math.random()*900)+100;
            System.out.println(random);
            Long time = System.currentTimeMillis();

            SysReceiveRecord sysReceiveRecord = new SysReceiveRecord();
            CopyUtils.copyProperties(sysAssetInfo, sysReceiveRecord);
            CopyUtils.copyProperties(req, sysReceiveRecord);

            //工单号生成
            sysReceiveRecord.setId(null);
            sysReceiveRecord.setJobNo(time+String.valueOf(random));
            sysReceiveRecord.setUsername(sysUserInfo.getUsername());
            sysReceiveRecord.setCreateTime(time);
            sysReceiveRecord.setAssetType(sysAssetInfo.getAssetType().intValue());
            sysReceiveRecord.setDepartmentId(sysUserInfo.getDepartmentId().intValue());

            SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(sysAssetInfo.getAssetType());

            SysUser userInfo = null;
            if (sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES) ||
                    sysAssetTypeInfo.getId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)
            ) {
                userInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("post_id", ParamsConstant.POST_GENERAL_ASSET_MANAGER));
            } else {
                userInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("post_id", ParamsConstant.POST_GROUP_ASSET_MANAGER));
            }

            // 审批人 -- 综合部资产管理员
            sysReceiveRecord.setFlowPath(userInfo.getId());
            sysReceiveRecordMapper.insertSelective(sysReceiveRecord);

            // 锁定库存，防止别人二次提交
            // 资产类型，如果是低值易耗品无需锁定，只需要判断当前数量是否大于0即可

            if (sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES) ||
                sysAssetTypeInfo.getId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)
            ) {

                String accessToken = AppletUtils.getAccessToken();

                return;
            }
            sysAssetMapper.update(null, new UpdateWrapper<SysAsset>()
                    .set("is_locked", ParamsConstant.ASSET_LOCKED_STATUS_ON).eq("asset_code", req.getAssetCode()));

            String accessToken = AppletUtils.getAccessToken();



        }catch (ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public SysReceiveRecordListResp find(Long id){
        try{

            //查询下是否存在
            SysReceiveRecordListResp info = sysReceiveRecordMapper.getOne(id);

            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            List<SysAuditLog> sysAuditLogList = new ArrayList<>();

            // 将申请人信息写入
            SysAuditLog sysAuditLog = new SysAuditLog();
            sysAuditLog.setAuditName(info.getUsername());  // 申请人姓名
            sysAuditLog.setAuditTime(info.getCreateTime()); // 申请时间
            sysAuditLogList.add(sysAuditLog);

            //根据id查询审批流程
            List<SysAuditLog> sysAuditLogs = sysAuditLogMapper.selectList(new QueryWrapper<SysAuditLog>().eq("related_id", id).eq("type", ParamsConstant.AUDIT_TYPE_FOR_RECEIVE).orderByAsc("id"));
            sysAuditLogList.addAll(sysAuditLogs);
            info.setSysAuditLogs(sysAuditLogList);
            return info;

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
            SysReceiveRecordListResp info = sysReceiveRecordMapper.getOne(id);

            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }else if(!info.getStatus().equals(ParamsConstant.AUDIT_STATUS_DEFAULT)){
                throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
            }

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));

            // 判断是撤销人员是否是资产领用申请人
            if (!sysUserInfo.getId().equals(info.getUserId())) {
                throw new ChorBizException(AmErrorCode.AUTHORIZATION_ERROR);
            }

            SysReceiveRecord record = new SysReceiveRecord();
            record.setId(id);
            record.setStatus(ParamsConstant.AUDIT_STATUS_BACKOUT);
            record.setFlowPath(ParamsConstant.AUDIT_STATUS_DEFAULT);
            record.setUpdateTime(System.currentTimeMillis());
            sysReceiveRecordMapper.updateByPrimaryKeySelective(record);
            sysAssetMapper.update(null,new UpdateWrapper<SysAsset>()
                    .set("is_locked",ParamsConstant.ASSET_LOCKED_STATUS_OFF).eq("asset_code",info.getAssetCode()));

        }catch(ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirmReceive(Long id){
        try{

            // 查询下是否存在
            SysReceiveRecordListResp info = sysReceiveRecordMapper.getOne(id);

            if (null == info) {

                throw new ChorBizException(AmErrorCode.NULL_FOUND);

            } else if(!info.getStatus().equals(ParamsConstant.AUDIT_STATUS_APPROVE) || !info.getFlowPath().equals(ParamsConstant.AUDIT_FLOWPAHT_FINISH)){

                throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
            }

            // 将资产领用记录状态设置为资产已领用
            SysReceiveRecord record = new SysReceiveRecord();
            record.setId(id);
            record.setStatus(ParamsConstant.RECEIVE_STATUS_RECEIVE);
            record.setUpdateTime(System.currentTimeMillis());
            sysReceiveRecordMapper.updateByPrimaryKeySelective(record);

            // 查询资产信息，出库数量和库存数对比
            SysAsset sysAssetInfo = sysAssetMapper.selectOne(new QueryWrapper<SysAsset>()
                    .eq("asset_code", info.getAssetCode()));
            if (null == sysAssetInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            int num = sysAssetInfo.getAssetNum() - sysAssetInfo.getOutNum() - sysAssetInfo.getChargeOffNum() - info.getNum();

            if (num < 0) {
                throw new ChorBizException(AmErrorCode.NOT_ENOUGH);
            }

            if (num == 0) {

                // 将资产库存状态设置为出库
                sysAssetMapper.outbound(info.getAssetCode(),ParamsConstant.ASSET_INVENTORY_STATUS_OUT,info.getNum(),System.currentTimeMillis());
            } else {
                sysAssetMapper.outbound(info.getAssetCode(),ParamsConstant.ASSET_INVENTORY_STATUS_IN,info.getNum(),System.currentTimeMillis());
            }

        }catch(ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void audit(SysAuditLogReq req, Long id) {

        try {
            SysReceiveRecord sysReceiveRecordInfo = sysReceiveRecordMapper.selectOne(new QueryWrapper<SysReceiveRecord>()
                    .eq("id", id).and(query-> query.eq("status", ParamsConstant.AUDIT_STATUS_ING).or().eq("status", ParamsConstant.AUDIT_STATUS_DEFAULT)));
            if (null == sysReceiveRecordInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            SysUser auditUserInfo = sysUserMapper.selectById(req.getAuditBy());

            // 判断审核人员是否为综合部管理员
            if (!(auditUserInfo.getPostId().equals(ParamsConstant.POST_GROUP_ASSET_MANAGER) || auditUserInfo.getPostId().equals(ParamsConstant.POST_GENERAL_ASSET_MANAGER))) {
                throw new ChorBizException(AmErrorCode.AUTHORIZATION_ERROR);
            }

            // TODO 此级别审核完毕后，判断是否是最后一道审核，是则结束,更改flow_path为 -1 处理结束，否则消息推送至上级，且更改flow_path 为上级id
            long currentTimeMillis = System.currentTimeMillis();
            SysAuditLog sysAuditLog = new SysAuditLog();
            CopyUtils.copyProperties(req, sysAuditLog);
            sysAuditLog.setAuditTime(currentTimeMillis);
            sysAuditLog.setRelatedId(id);
            sysAuditLog.setType(ParamsConstant.AUDIT_TYPE_FOR_RECEIVE);
            sysAuditLog.setAuditName(auditUserInfo.getUsername());
            sysAuditLogMapper.insertSelective(sysAuditLog);


            // 消息审核状态变更为已审核
            sysMessageMapper.update(null, new UpdateWrapper<SysMessage>()
                    .set("audit_status", ParamsConstant.AUDIT_FLOWPAHT_FINISH)
                    .eq("type", ParamsConstant.AUDIT_TYPE_FOR_RECEIVE)
                    .eq("related_id", id)
                    .eq("to_user", req.getAuditBy()));

            sysReceiveRecordInfo.setStatus(req.getAuditStatus());
            sysReceiveRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
            sysReceiveRecordMapper.updateById(sysReceiveRecordInfo);


            if (req.getAuditStatus().equals(ParamsConstant.AUDIT_STATUS_REJECT)) {
                sysAssetMapper.update(null,new UpdateWrapper<SysAsset>()
                        .set("is_locked",ParamsConstant.ASSET_LOCKED_STATUS_OFF).eq("asset_code",sysReceiveRecordInfo.getAssetCode()));
            }

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public List<FlowPathResp> auditFlowList(Long id) {

        try {

            // 获取领用信息
            SysReceiveRecord sysReceiveRecordInfo = sysReceiveRecordMapper.selectById(id);
            if (null == sysReceiveRecordInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            List<FlowPathResp> flowPathRespList = new ArrayList<>();
            FlowPathResp receiveUser = new FlowPathResp();
            receiveUser.setUsername(sysReceiveRecordInfo.getUsername());
            receiveUser.setUserId(sysReceiveRecordInfo.getUserId());
            receiveUser.setPostName("申请人");
            flowPathRespList.add(receiveUser); // 申请人


            // 获取集团资产管理员信息
            SysUser groupUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                    .eq("department_id", ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT).eq("post_id", ParamsConstant.POST_GROUP_ASSET_MANAGER).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));

            // 综合部管理员
            SysUser generalUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                    .eq("department_id", ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT).eq("post_id", ParamsConstant.POST_GENERAL_ASSET_MANAGER).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));


            Integer assetType = sysReceiveRecordInfo.getAssetType();
            SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(assetType);
            if (sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)) {

                FlowPathResp resp = new FlowPathResp();

                // 查看有无审批记录
                Integer count = sysAuditLogMapper.selectCount(new QueryWrapper<SysAuditLog>().eq("related_id", id).eq("type", ParamsConstant.AUDIT_TYPE_FOR_RECEIVE));
                resp.setPostName("审批人");
                if (count == 0) {
                    // todo something just u like
                    resp.setUserId(0);
                    resp.setUsername("邹磊/曾宇琦");
                } else {

                    SysAuditLog groupSysAuditLogInfo = sysAuditLogMapper.selectOne(new QueryWrapper<SysAuditLog>()
                            .eq("type", ParamsConstant.AUDIT_TYPE_FOR_RECEIVE).eq("related_id", id).eq("audit_by", groupUserInfo.getId()));

                    if (null != groupSysAuditLogInfo) {

                        resp.setUserId(groupUserInfo.getId());
                        resp.setUsername(groupUserInfo.getUsername());
                        resp.setAuditStatus(groupSysAuditLogInfo.getAuditStatus());
                        resp.setReason(groupSysAuditLogInfo.getReason());
                    } else {
                        SysAuditLog generalSysAuditLogInfo = sysAuditLogMapper.selectOne(new QueryWrapper<SysAuditLog>()
                                .eq("type", ParamsConstant.AUDIT_TYPE_FOR_RECEIVE).eq("related_id", id).eq("audit_by", generalUserInfo.getId()));
                        if (null != generalSysAuditLogInfo) {
                            resp.setUserId(generalUserInfo.getId());
                            resp.setUsername(generalUserInfo.getUsername());
                            resp.setAuditStatus(generalSysAuditLogInfo.getAuditStatus());
                            resp.setReason(generalSysAuditLogInfo.getReason());
                        }
                    }
                }

                flowPathRespList.add(resp);

            } else {
                FlowPathResp flowPathResp = getFlowPathResp(groupUserInfo, id);
                flowPathRespList.add(flowPathResp);
            }



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
                SysReceiveRecord sysReceiveRecordInfo = sysReceiveRecordMapper.selectOne(new QueryWrapper<SysReceiveRecord>()
                        .eq("id", id).and(query-> query.eq("status", ParamsConstant.AUDIT_STATUS_ING).or().eq("status", ParamsConstant.AUDIT_STATUS_DEFAULT)));
                if (null == sysReceiveRecordInfo) {
                    throw new ChorBizException(AmErrorCode.NULL_FOUND);
                }

                SysUser auditUserInfo = sysUserMapper.selectById(auditBy);

                // 判断审核人员是否为综合部管理员
                if (!(auditUserInfo.getPostId().equals(ParamsConstant.POST_GROUP_ASSET_MANAGER) || auditUserInfo.getPostId().equals(ParamsConstant.POST_GENERAL_ASSET_MANAGER))) {
                    throw new ChorBizException(AmErrorCode.AUTHORIZATION_ERROR);
                }

                // TODO 此级别审核完毕后，判断是否是最后一道审核，是则结束,更改flow_path为 -1 处理结束，否则消息推送至上级，且更改flow_path 为上级id
                long currentTimeMillis = System.currentTimeMillis();
                SysAuditLog sysAuditLog = new SysAuditLog();
                CopyUtils.copyProperties(req, sysAuditLog);
                sysAuditLog.setAuditTime(currentTimeMillis);
                sysAuditLog.setRelatedId(id);
                sysAuditLog.setType(ParamsConstant.AUDIT_TYPE_FOR_RECEIVE);
                sysAuditLog.setAuditName(auditUserInfo.getUsername());
                sysAuditLog.setAuditBy(auditBy);
                sysAuditLogMapper.insertSelective(sysAuditLog);


                // 消息审核状态变更为已审核
                sysMessageMapper.update(null, new UpdateWrapper<SysMessage>()
                        .set("audit_status", ParamsConstant.AUDIT_FLOWPAHT_FINISH)
                        .eq("type", ParamsConstant.AUDIT_TYPE_FOR_RECEIVE)
                        .eq("related_id", id)
                        .eq("to_user", auditBy));

                sysReceiveRecordInfo.setStatus(req.getAuditStatus());
                sysReceiveRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                sysReceiveRecordMapper.updateById(sysReceiveRecordInfo);


                if (req.getAuditStatus().equals(ParamsConstant.AUDIT_STATUS_REJECT)) {
                    sysAssetMapper.update(null,new UpdateWrapper<SysAsset>()
                            .set("is_locked",ParamsConstant.ASSET_LOCKED_STATUS_OFF).eq("asset_code",sysReceiveRecordInfo.getAssetCode()));
                }
            }


        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
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
                .eq("type", ParamsConstant.AUDIT_TYPE_FOR_RECEIVE).eq("related_id", relatedId).eq("audit_by", currentUser.getId()));

        if (null != sysAuditLog) {
            flowPathResp.setAuditStatus(sysAuditLog.getAuditStatus());
            flowPathResp.setReason(sysAuditLog.getReason());
        }

        return flowPathResp;
    }
}
