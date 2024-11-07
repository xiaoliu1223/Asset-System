package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.*;
import com.cg.test.am.model.*;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysApplicationRecordService;
import com.cg.test.am.utils.AppletUtils;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysApplicationRecordListResp;
import com.cg.test.am.vo.response.SysApplicationRecordResp;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysApplicationRecordServiceImpl implements SysApplicationRecordService {

    @Resource
    SysApplicationRecordMapper sysApplicationRecordMapper;

    @Resource
    SysAuditLogMapper sysAuditLogMapper;

    @Resource
    SysAssetTypeMapper sysAssetTypeMapper;

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysPurchaseRecordMapper sysPurchaseRecordMapper;

    @Resource
    HttpServletRequest request;

    @Resource
    SysPostMapper sysPostMapper;

    @Resource
    SysMessageMapper sysMessageMapper;

    @Resource
    SysWorkflowMapper sysWorkflowMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String save(SysApplicationRecord sysApplicationRecord) {

        try {

            if (sysApplicationRecord.getNum() == null ||sysApplicationRecord.getNum() <= 0) {
                throw new ChorBizException(AmErrorCode.NUM_ERROR);
            }

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));

            //根据类型id查询最上级id 只有低值易耗品 数量才能存在多个
            SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(sysApplicationRecord.getAssetType());
            if(!sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)
                    && sysApplicationRecord.getNum()>1){
                throw new ChorBizException(AmErrorCode.NUM_ABNORMAL);
            }

            //工单号
            // todo 计数器 formatTime + ParamsConstant.AUDIT_TYPE_FOR_APPLICATION + xxx1 总计 8 + 1 + 4 13位
            int random = (int)(Math.random()*900)+100;
            Long time = System.currentTimeMillis();
            String jobNo = time+String.valueOf(random);
            sysApplicationRecord.setJobNo(jobNo);
            if(sysApplicationRecord.getRelateJobNo()==null){
                sysApplicationRecord.setRelateJobNo(jobNo);
            }

            //申请单位id
            sysApplicationRecord.setDepartmentId(sysUserInfo.getDepartmentId());

            //申请人信息
            sysApplicationRecord.setUsername(sysUserInfo.getUsername());
            sysApplicationRecord.setUserId(sysUserInfo.getId());

            // todo 记录新增时，直接插入flow_path为审核人 其他的申请逻辑按此操作
            sysApplicationRecord.setCreateTime(time);
            sysApplicationRecord.setStatus(ParamsConstant.AUDIT_STATUS_DEFAULT);


            Integer auditUserId = findAuditUserId(sysUserInfo, sysAssetTypeInfo.getSuperId(), sysUserInfo.getDepartmentId());
//            SysUser superUser = superUser(sysUserInfo);
            sysApplicationRecord.setFlowPath(auditUserId);
            sysApplicationRecordMapper.insertSelective(sysApplicationRecord);

            // 接入小程序消息推送 flow_path 为审核人id
            // todo accessToken, 从redis中获取
            SysUser superUser = sysUserMapper.selectById(auditUserId);
            String accessToken = AppletUtils.getAccessToken();
            return sysApplicationRecord.getRelateJobNo();

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public Map<String, Object> list(SysApplicationRecordListReq req) {

        try {

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            String departmentIds = claims.get("fillArgs").toString();
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));

            if(req.getDepartmentId() == null){
                if (sysUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)) {
                    // 综合部的可以查看所有部门的资产信息
                    req.setDepartmentIds(null);
                } else {
                    req.setDepartmentIds(departmentIds);
                }
            }else{
                req.setDepartmentIds(req.getDepartmentId());
            }

            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysApplicationRecordMapper.count(req);
            List<SysApplicationRecordListResp> list = sysApplicationRecordMapper.list(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public Map<String, Object> auditList(AuditListReq req){

        try {
            req.setBatchSingle(1);
            Page<SysApplicationRecordListResp> page = new Page<>(req.getCurrent(), req.getLimit());
            IPage<SysApplicationRecordListResp> resp = sysApplicationRecordMapper.getPage(page, req);

//            Integer limit = req.getLimit();
//            Integer offset = (req.getCurrent() - 1) * limit;
//            int count = sysApplicationRecordMapper.auditCount(req);
//            List<SysApplicationRecordListResp> list = sysApplicationRecordMapper.auditList(req, offset, limit);



            Map<String, Object> map = new HashMap<>();
            map.put("total_record", resp.getTotal());
            map.put("data", resp.getRecords());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public SysApplicationRecordResp find(Long id){
        try {
            SysApplicationRecordResp sysApplicationRecordResp = sysApplicationRecordMapper.getById(id);
            if (null == sysApplicationRecordResp) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            List<SysAuditLog> sysAuditLogList = new ArrayList<>();

            // 将申请人信息写入
            SysAuditLog sysAuditLog = new SysAuditLog();
            sysAuditLog.setAuditName(sysApplicationRecordResp.getUsername());  // 申请人姓名
            sysAuditLog.setAuditTime(sysApplicationRecordResp.getCreateTime()); // 申请时间
            sysAuditLog.setPostName("申请人");
            sysAuditLogList.add(sysAuditLog);

            //根据id查询审批流程
            List<SysAuditLog> sysAuditLogs = sysAuditLogMapper.selectList(new QueryWrapper<SysAuditLog>().eq("related_id", id).eq("type", ParamsConstant.AUDIT_TYPE_FOR_APPLICATION).orderByAsc("id"));
            sysAuditLogs.forEach(sal->{
                sal.setPostName("审批人");
            });
            sysAuditLogList.addAll(sysAuditLogs);

            sysApplicationRecordResp.setSysAuditLogs(sysAuditLogList);
            if(sysApplicationRecordResp.getBudgetPrice()!=null && sysApplicationRecordResp.getNum()!=null){
                sysApplicationRecordResp.setTotalPrice(sysApplicationRecordResp.getBudgetPrice().multiply(new BigDecimal(sysApplicationRecordResp.getNum())));
            }
            return sysApplicationRecordResp;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(SysApplicationRecordReq req, Long id) {

        try {

            if (req.getNum() <= 0 || req.getNum() == null) {
                throw new ChorBizException(AmErrorCode.NUM_ERROR);
            }

            SysApplicationRecord sysApplicationRecordInfo = sysApplicationRecordMapper.selectByPrimaryKey(id);

            if (null == sysApplicationRecordInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            if(!sysApplicationRecordInfo.getStatus().equals(ParamsConstant.AUDIT_STATUS_DEFAULT)){
                throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
            }

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));
            if (!sysUserInfo.getId().equals(sysApplicationRecordInfo.getUserId())) {
                throw new ChorBizException(AmErrorCode.AUTHORIZATION_ERROR);
            }

            //根据类型id查询最上级id 只有低值易耗品 数量才能存在多个
            SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(req.getAssetType());
            if((!sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES))
                    && req.getNum()>1){
                throw new ChorBizException(AmErrorCode.NUM_ABNORMAL);
            }
            CopyUtils.copyProperties(req, sysApplicationRecordInfo);
            long updateTime = System.currentTimeMillis();
            sysApplicationRecordInfo.setUpdateTime(updateTime);
            sysApplicationRecordMapper.updateById(sysApplicationRecordInfo);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void  undo(Long id){
        try{

            SysApplicationRecord sysApplicationRecordInfo = sysApplicationRecordMapper.selectByPrimaryKey(id);

            if (null == sysApplicationRecordInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            if(!sysApplicationRecordInfo.getStatus().equals(ParamsConstant.AUDIT_STATUS_DEFAULT)){
                throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
            }

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));
            if (!sysUserInfo.getId().equals(sysApplicationRecordInfo.getUserId())) {
                throw new ChorBizException(AmErrorCode.AUTHORIZATION_ERROR);
            }

            SysApplicationRecord res = new SysApplicationRecord();
            res.setId(id);
            res.setStatus(ParamsConstant.UNDO);
            res.setFlowPath(ParamsConstant.AUDIT_STATUS_DEFAULT);
            sysApplicationRecordMapper.updateById(res);

        }catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

/*    @Deprecated
    @Transactional(rollbackFor = Exception.class)
    public void audit1(SysAuditLogReq req, Long id) {
        try{

            SysApplicationRecord sysApplicationRecordInfo = sysApplicationRecordMapper.selectOne(new QueryWrapper<SysApplicationRecord>()
                    .eq("id", id).and(query-> query.eq("status", ParamsConstant.AUDIT_STATUS_ING).or().eq("status", ParamsConstant.AUDIT_STATUS_DEFAULT)));
            if (null == sysApplicationRecordInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            SysUser auditUserInfo = sysUserMapper.selectById(req.getAuditBy());

            // TODO 此级别审核完毕后，判断是否是最后一道审核，是则结束,更改flow_path为 -1 处理结束，否则消息推送至上级，且更改flow_path 为上级id
            long currentTimeMillis = System.currentTimeMillis();
            SysAuditLog sysAuditLog = new SysAuditLog();
            CopyUtils.copyProperties(req, sysAuditLog);
            sysAuditLog.setAuditTime(currentTimeMillis);
            sysAuditLog.setRelatedId(id);
            sysAuditLog.setType(ParamsConstant.AUDIT_TYPE_FOR_APPLICATION);
            sysAuditLog.setAuditName(auditUserInfo.getUsername());
            sysAuditLogMapper.insertSelective(sysAuditLog);

            // 消息审核状态变更为已审核
            sysMessageMapper.update(null, new UpdateWrapper<SysMessage>()
                    .set("audit_status", ParamsConstant.AUDIT_FLOWPAHT_FINISH)
                    .eq("type", ParamsConstant.AUDIT_TYPE_FOR_APPLICATION)
                    .eq("related_id", id)
                    .eq("to_user", req.getAuditBy()));

            // 审核结果驳回，则终止，不往上提交
            if (req.getAuditStatus().equals(ParamsConstant.AUDIT_STATUS_REJECT)) {
                sysApplicationRecordInfo.setStatus(req.getAuditStatus());
                sysApplicationRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                sysApplicationRecordMapper.updateById(sysApplicationRecordInfo);
                return;
            }

            // 采购记录
            SysPurchaseRecord sysPurchaseRecord = null;

            //（1）低值易耗品：各单位资产管理员—单位经理—集团资产管理员—综合管理部经理
            //（2）办公资产、固定资产：申请人-申请人单位负责人-申请人单位分管领导—集团资产管理员—综合管理部经理—综合管理部分管领导—总经理
            SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(sysApplicationRecordInfo.getAssetType());

            // 获取上级
            SysUser superUser = superUser(auditUserInfo);

            // 获取综合部资产管理员
            SysUser assetUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                    .eq("post_id", ParamsConstant.POST_GROUP_ASSET_MANAGER));
            sysApplicationRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_ING);
            if (sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)||sysAssetTypeInfo.getId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)) {

                // 低值易耗品
                // 判断是不是综合管理部经理
                if ( auditUserInfo.getPostId().equals(ParamsConstant.POST_GROUP_MANAGER_LEADER)) {

                    sysApplicationRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_APPROVE);
                    // 审批结束，将flow_path 变更为 -1
                    sysApplicationRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                    sysApplicationRecordMapper.updateById(sysApplicationRecordInfo);

                    //采购添加
                    sysPurchaseRecord = purchase(sysApplicationRecordInfo);

                } else {

                    // 判断是否为综合管理部
                    if (auditUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)) {

                        //  填入上级
                        sysApplicationRecordInfo.setFlowPath(superUser.getId());

                    } else {
                        if (ParamsConstant.POST_TYPE_DEPARTMENT_MANAGER.contains(auditUserInfo.getPostId())) {
                            sysApplicationRecordInfo.setFlowPath(assetUser.getId());
                        }  else {
                            if (ParamsConstant.POST_TYPE_MANAGER_LEADER.contains(superUser.getPostId())
                                    || ParamsConstant.POST_TYPE_GENERAL_MANAGER.equals(superUser.getPostId())) {

                                sysApplicationRecordInfo.setFlowPath(assetUser.getId());
                            }
                            sysApplicationRecordInfo.setFlowPath(superUser.getId());
                        }
                    }
                }
            } else if (sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_OFFICE)
                    || sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_FIXED)
                    ||sysAssetTypeInfo.getId().equals(ParamsConstant.ASSET_TYPE_OFFICE)
                    || sysAssetTypeInfo.getId().equals(ParamsConstant.ASSET_TYPE_FIXED)) {

                // 办公资产 和 固定资产
                // 判断是不是总经理

                if ( auditUserInfo.getPostId().equals(ParamsConstant.POST_TYPE_GENERAL_MANAGER)) {
                    sysApplicationRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_APPROVE);
                    // 审批结束，将flow_path 变更为 -1
                    sysApplicationRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                    sysApplicationRecordMapper.updateById(sysApplicationRecordInfo);

                    //采购添加
                    sysPurchaseRecord = purchase(sysApplicationRecordInfo);

                } else {

                    // 判断是否为综合管理部
                    if (auditUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)) {

                        //  填入上级
                        sysApplicationRecordInfo.setFlowPath(superUser.getId());

                    } else {
                        if (ParamsConstant.POST_TYPE_MANAGER_LEADER.contains(auditUserInfo.getPostId())
                                || ParamsConstant.POST_TYPE_GENERAL_MANAGER.equals(superUser.getPostId())) {

                            // 综合部管理员
                            sysApplicationRecordInfo.setFlowPath(assetUser.getId());
                        }  else {
                            sysApplicationRecordInfo.setFlowPath(superUser.getId());
                        }
                    }
                }
            }
            sysApplicationRecordMapper.updateById(sysApplicationRecordInfo);

            // 消息推送 ---如果审批未结束则推送至上级
            String accessToken = AppletUtils.getAccessToken();
            MessageUtils messageUtils = new MessageUtils();
            if (!sysApplicationRecordInfo.getFlowPath().equals(ParamsConstant.AUDIT_FLOWPAHT_FINISH)) {

                // 综合管理部
                if (auditUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)) {
                    messageUtils.sendMessage(superUser, "您好，有新的资产申请信息待您审批", ParamsConstant.MESSAGE_TYPE_FOR_APPLICATION, id, accessToken);
                } else {

                    if (ParamsConstant.POST_TYPE_MANAGER_LEADER.contains(auditUserInfo.getPostId())
                            || ParamsConstant.POST_TYPE_GENERAL_MANAGER.equals(superUser.getPostId())) {
                        messageUtils.sendMessage(assetUser, "您好，有新的资产申请信息待您审批", ParamsConstant.MESSAGE_TYPE_FOR_APPLICATION, id, accessToken);
                    }  else {
                        messageUtils.sendMessage(superUser, "您好，有新的资产申请信息待您审批", ParamsConstant.MESSAGE_TYPE_FOR_APPLICATION, id, accessToken);
                    }
                }
            } else if (sysApplicationRecordInfo.getFlowPath().equals(ParamsConstant.AUDIT_FLOWPAHT_FINISH) && req.getAuditStatus().equals(ParamsConstant.AUDIT_STATUS_APPROVE)) {

                // sysPurchaseRecord
                messageUtils.sendMessage(assetUser, "您好，有新的资产采购信息待您处理", ParamsConstant.MESSAGE_TYPE_FOR_PURCHASE, sysPurchaseRecord.getId(), accessToken);
            }

        }catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }*/

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void audit(SysAuditLogReq req, Long id) {

        SysApplicationRecord sysApplicationRecordInfo = sysApplicationRecordMapper.selectOne(new QueryWrapper<SysApplicationRecord>()
                .eq("id", id).and(query-> query.eq("status", ParamsConstant.AUDIT_STATUS_ING).or().eq("status", ParamsConstant.AUDIT_STATUS_DEFAULT)));
        if (null == sysApplicationRecordInfo) {
            throw new ChorBizException(AmErrorCode.NULL_FOUND);
        }

        if(sysApplicationRecordInfo.getRelateJobNo()!=null){
            List<SysApplicationRecord> sysApplicationRecordInfoList = sysApplicationRecordMapper.selectList(new QueryWrapper<SysApplicationRecord>()
                    .eq("relate_job_no", sysApplicationRecordInfo.getRelateJobNo()));
            for(SysApplicationRecord s : sysApplicationRecordInfoList){
                auditLogic(s, req, s.getId());
            }
        }else{
            auditLogic(sysApplicationRecordInfo, req, id);
        }
    }

    private void auditLogic(SysApplicationRecord sysApplicationRecordInfo, SysAuditLogReq req, Long id){
        try{

            // todo 写的不够严谨，按理来说需要判断是否pinAPI，暂时先这样吧，后期修改，判断当前操作用户是否是该条记录flowPath指定审批用户
            SysUser auditUserInfo = sysUserMapper.selectById(sysApplicationRecordInfo.getFlowPath());
            SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(sysApplicationRecordInfo.getAssetType());
            Integer nextAuditUserId = findAuditUserId(auditUserInfo, sysAssetTypeInfo.getSuperId(), sysApplicationRecordInfo.getDepartmentId());

            // 此级别审核完毕后，判断是否是最后一道审核，是则结束,更改flow_path为 -1 处理结束，否则消息推送至上级，且更改flow_path 为上级id
            long currentTimeMillis = System.currentTimeMillis();
            SysAuditLog sysAuditLog = new SysAuditLog();
            CopyUtils.copyProperties(req, sysAuditLog);
            sysAuditLog.setAuditBy(sysApplicationRecordInfo.getFlowPath());
            sysAuditLog.setAuditTime(currentTimeMillis);
            sysAuditLog.setRelatedId(id);
            sysAuditLog.setType(ParamsConstant.AUDIT_TYPE_FOR_APPLICATION);
            sysAuditLog.setAuditName(auditUserInfo.getUsername());
            sysAuditLogMapper.insertSelective(sysAuditLog);

            // 消息审核状态变更为已审核
            sysMessageMapper.update(null, new UpdateWrapper<SysMessage>()
                    .set("audit_status", ParamsConstant.AUDIT_FLOWPAHT_FINISH)
                    .eq("type", ParamsConstant.AUDIT_TYPE_FOR_APPLICATION)
                    .eq("related_id", id)
                    .eq("to_user", sysApplicationRecordInfo.getFlowPath()));

            // 审核结果驳回，则终止，不往上提交
            if (req.getAuditStatus().equals(ParamsConstant.AUDIT_STATUS_REJECT)) {
                sysApplicationRecordInfo.setStatus(req.getAuditStatus());
                sysApplicationRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                sysApplicationRecordMapper.updateById(sysApplicationRecordInfo);
                return;
            }

            // 消息推送 ---如果审批未结束则推送至上级
            String accessToken = AppletUtils.getAccessToken();

            if (nextAuditUserId.equals(ParamsConstant.AUDIT_FLOWPAHT_FINISH)) {

                sysApplicationRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_APPROVE);
                sysApplicationRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                sysApplicationRecordMapper.updateById(sysApplicationRecordInfo);

                // 采购记录
                SysPurchaseRecord sysPurchaseRecord = purchase(sysApplicationRecordInfo);

                // 推送给对应的资产管理员
                String postId = sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES) ? ParamsConstant.POST_GENERAL_ASSET_MANAGER : ParamsConstant.POST_GROUP_ASSET_MANAGER;

                SysUser assetUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                        .eq("post_id", postId).eq("department_id", ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));


                //低值易耗品抄送
                if(sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)){
                    SysUser copyToUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", ParamsConstant.CONSUMABLES_COPYTO_USERID));
                    String msg = "【抄送】"+sysApplicationRecordInfo.getUsername()+"申请了"+sysApplicationRecordInfo.getNum()
                            +sysApplicationRecordInfo.getUnits()+sysApplicationRecordInfo.getAssetName();
                }
                return;
            }

            // 委托给next审批
            sysApplicationRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_ING);
            sysApplicationRecordInfo.setFlowPath(nextAuditUserId);
            sysApplicationRecordMapper.updateById(sysApplicationRecordInfo);
            SysUser nextAuditUser = sysUserMapper.selectById(nextAuditUserId);

        }catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public List<FlowPathResp> auditFlowList(Long relatedId) {
        try {

            SysApplicationRecord sysApplicationRecordInfo = sysApplicationRecordMapper.selectById(relatedId);
            if (null == sysApplicationRecordInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            // 获取申请部门
            Integer departmentId = sysApplicationRecordInfo.getDepartmentId().intValue();

            // 获取资产类别
            SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(sysApplicationRecordInfo.getAssetType());

            Map<String, Object> params = new HashMap<>();
            params.put("departmentId", departmentId);
            params.put("relatedId", relatedId);

            Long assetType = sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES) ? ParamsConstant.ASSET_TYPE_CONSUMABLES : ParamsConstant.ASSET_TYPE_FIXED;
            params.put("assetType", assetType);
            params.put("auditType", ParamsConstant.AUDIT_TYPE_FOR_APPLICATION);
            List<FlowPathResp> respList = sysWorkflowMapper.getWorkflowList(params);

//            List<FlowPathResp> flowPathRespList = new ArrayList<>();
            FlowPathResp applicationUser = new FlowPathResp();
            applicationUser.setUsername(sysApplicationRecordInfo.getUsername());
            applicationUser.setUserId(sysApplicationRecordInfo.getUserId());
//            String postName = respList.get(0).getPostName();
            applicationUser.setPostName("申请人");

//            flowPathRespList.add(applicationUser); // 申请人

            respList.remove(0);
            respList.add(0, applicationUser);


            return respList;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchAudit(SysAuditLogBatchReq req, Integer auditBy) {

        try{
            for (Long id: req.getIdList()) {
                SysApplicationRecord sysApplicationRecordInfo = sysApplicationRecordMapper.selectOne(new QueryWrapper<SysApplicationRecord>()
                        .eq("id", id).and(query-> query.eq("status", ParamsConstant.AUDIT_STATUS_ING).or().eq("status", ParamsConstant.AUDIT_STATUS_DEFAULT)));

                if (null == sysApplicationRecordInfo) {
                    throw new ChorBizException(AmErrorCode.NULL_FOUND);
                }

                // todo 写的不够严谨，按理来说需要判断是否pinAPI，暂时先这样吧，后期修改，判断当前操作用户是否是该条记录flowPath指定审批用户
                SysUser auditUserInfo = sysUserMapper.selectById(sysApplicationRecordInfo.getFlowPath());

                // 此处为何注释掉，因为邹磊要求他能够帮助同部门的人审核
//                if (!auditBy.equals(auditUserInfo.getId())) {
//                    throw new ChorBizException(AmErrorCode.NULL_FOUND);
//                }
                SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(sysApplicationRecordInfo.getAssetType());
                Integer nextAuditUserId = findAuditUserId(auditUserInfo, sysAssetTypeInfo.getSuperId(), sysApplicationRecordInfo.getDepartmentId());

                // 此级别审核完毕后，判断是否是最后一道审核，是则结束,更改flow_path为 -1 处理结束，否则消息推送至上级，且更改flow_path 为上级id
                long currentTimeMillis = System.currentTimeMillis();
                SysAuditLog sysAuditLog = new SysAuditLog();
                CopyUtils.copyProperties(req, sysAuditLog);
                sysAuditLog.setAuditBy(sysApplicationRecordInfo.getFlowPath());
                sysAuditLog.setAuditTime(currentTimeMillis);
                sysAuditLog.setRelatedId(id);
                sysAuditLog.setType(ParamsConstant.AUDIT_TYPE_FOR_APPLICATION);
                sysAuditLog.setAuditName(auditUserInfo.getUsername());
                sysAuditLogMapper.insertSelective(sysAuditLog);

                // 消息审核状态变更为已审核
                sysMessageMapper.update(null, new UpdateWrapper<SysMessage>()
                        .set("audit_status", ParamsConstant.AUDIT_FLOWPAHT_FINISH)
                        .eq("type", ParamsConstant.AUDIT_TYPE_FOR_APPLICATION)
                        .eq("related_id", id)
                        .eq("to_user", sysApplicationRecordInfo.getFlowPath()));

                // 审核结果驳回，则终止，不往上提交
                if (req.getAuditStatus().equals(ParamsConstant.AUDIT_STATUS_REJECT)) {
                    sysApplicationRecordInfo.setStatus(req.getAuditStatus());
                    sysApplicationRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                    sysApplicationRecordMapper.updateById(sysApplicationRecordInfo);
                    return;
                }

                // 消息推送 ---如果审批未结束则推送至上级
                String accessToken = AppletUtils.getAccessToken();

                if (nextAuditUserId.equals(ParamsConstant.AUDIT_FLOWPAHT_FINISH)) {

                    sysApplicationRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_APPROVE);
                    sysApplicationRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                    sysApplicationRecordMapper.updateById(sysApplicationRecordInfo);

                    // 采购记录
                    SysPurchaseRecord sysPurchaseRecord = purchase(sysApplicationRecordInfo);

                    // 推送给对应的资产管理员
                    String postId = sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES) ? ParamsConstant.POST_GENERAL_ASSET_MANAGER : ParamsConstant.POST_GROUP_ASSET_MANAGER;

                    SysUser assetUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                            .eq("post_id", postId).eq("department_id", ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));

                    return;
                }

                // 委托给next审批
                sysApplicationRecordInfo.setStatus(ParamsConstant.AUDIT_STATUS_ING);
                sysApplicationRecordInfo.setFlowPath(nextAuditUserId);
                sysApplicationRecordMapper.updateById(sysApplicationRecordInfo);
                SysUser nextAuditUser = sysUserMapper.selectById(nextAuditUserId);
            }

        }catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public List<SysApplicationRecordResp> getByRelateJobNo(String relateJobNo) {
        return sysApplicationRecordMapper.getByRelateJobNo(relateJobNo);
    }

    /**
     * 新增采购记录
     * @param sysApplicationRecord
     */
    public SysPurchaseRecord purchase(SysApplicationRecord sysApplicationRecord) {

        SysPurchaseRecord sysPurchaseRecord = new SysPurchaseRecord();
        CopyUtils.copyProperties(sysApplicationRecord, sysPurchaseRecord);

        sysPurchaseRecord.setId(null);
        sysPurchaseRecord.setCreateTime(System.currentTimeMillis());
        sysPurchaseRecord.setBudgetNum(sysApplicationRecord.getNum());
        sysPurchaseRecord.setBudgetPrice(sysApplicationRecord.getBudgetPrice());
        sysPurchaseRecord.setPurchaseStatus(ParamsConstant.AUDIT_STATUS_DEFAULT);
        sysPurchaseRecord.setDepartmentId(sysApplicationRecord.getDepartmentId().intValue());
        sysPurchaseRecordMapper.insertSelective(sysPurchaseRecord);
        return sysPurchaseRecord;
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
        SysPost assetUserPost = sysPostMapper.selectById(currentUser.getPostId());
        flowPathResp.setPostName(assetUserPost.getName());

        // 查看审批状态
        SysAuditLog sysAuditLog = sysAuditLogMapper.selectOne(new QueryWrapper<SysAuditLog>()
                .eq("type", ParamsConstant.AUDIT_TYPE_FOR_APPLICATION).eq("related_id", relatedId).eq("audit_by", currentUser.getId()));

        if (null != sysAuditLog) {
            flowPathResp.setAuditStatus(sysAuditLog.getAuditStatus());
            flowPathResp.setReason(sysAuditLog.getReason());
        }

        return flowPathResp;
    }


    /**
     * 获取上级审批用户id
     * @param currentUser
     * @param assetType
     * @param departmentId 申请人部门id
     * @return
     */
    public Integer findAuditUserId(SysUser currentUser, Long assetType, Long departmentId) {

        Long at = assetType.equals(ParamsConstant.ASSET_TYPE_CONSUMABLES) ? ParamsConstant.ASSET_TYPE_CONSUMABLES : ParamsConstant.ASSET_TYPE_FIXED;

        SysWorkflow sysWorkflowInfo = sysWorkflowMapper.selectOne(new QueryWrapper<SysWorkflow>()
                .eq("department_id", departmentId).eq("audit_type", ParamsConstant.AUDIT_TYPE_FOR_APPLICATION)
                .eq("user_id", currentUser.getId()).eq("asset_type", at).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));

        if (null == sysWorkflowInfo) {
            SysWorkflow sysWorkflow = sysWorkflowMapper.selectOne(new QueryWrapper<SysWorkflow>()
                    .eq("department_id", departmentId).eq("audit_type", ParamsConstant.AUDIT_TYPE_FOR_APPLICATION)
                    .eq("asset_type", at).eq("user_id", 0).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            return sysWorkflow.getSuperUserId();

        }
        return sysWorkflowInfo.getSuperUserId();
    }


}
