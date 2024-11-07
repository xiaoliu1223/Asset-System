package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.*;
import com.cg.test.am.model.*;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysReturnRecordService;
import com.cg.test.am.utils.AppletUtils;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysReceiveRecordListResp;
import com.cg.test.am.vo.response.SysReturnRecordResp;
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
public class SysReturnRecordServiceImpl implements SysReturnRecordService {

    @Resource
    SysReceiveRecordMapper sysReceiveRecordMapper;
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    SysReturnRecordMapper sysReturnRecordMapper;
    @Resource
    SysAuditLogMapper sysAuditLogMapper;
    @Resource
    SysAssetMapper sysAssetMapper;

    @Resource
    SysPostMapper sysPostMapper;

    @Resource
    HttpServletRequest request;

    @Resource
    SysMessageMapper sysMessageMapper;

    @Resource
    SysAssetTypeMapper sysAssetTypeMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysReturnRecordReq req){
           try{

               //根据用户id查询归还者姓名
               SysUser sysUserInfo = sysUserMapper.selectById(req.getUserId());

               //根据领用id查询领用记录
               SysReceiveRecordListResp sysReceiveRecord = sysReceiveRecordMapper.getOne(req.getReceiveId());
               if (null == sysReceiveRecord) {
                   throw new ChorBizException(AmErrorCode.NULL_FOUND);
               }else if(sysReceiveRecord.getStatus()!=ParamsConstant.RECEIVE_STATUS_RECEIVE){
                   throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
               }

               if (!sysReceiveRecord.getDepartmentId().equals(sysUserInfo.getDepartmentId().intValue())) {
                   throw new ChorBizException(AmErrorCode.ASSET_NOT_BELONG);
               }

               if (!req.getUserId().equals(sysReceiveRecord.getUserId())) {
                   throw new ChorBizException(AmErrorCode.NO_AUTHORIZATION);
               }

               SysAssetType sysAssetType = sysAssetTypeMapper.selectById(sysReceiveRecord.getAssetType());
               if (sysAssetType.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES) || sysAssetType.getId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)) {
                   throw new ChorBizException(AmErrorCode.ASSET_TYPE_RETURN_ERROR);
               }

               //使用年限计算，归还时间减去领用时间
               Long now = System.currentTimeMillis();

               double difference = (double) (now - sysReceiveRecord.getUpdateTime() ) / 1000;
               double d = difference/86400/ 365;
               Integer usedAge = (int) Math.ceil(d);
               SysReturnRecord sysReturnRecord = new SysReturnRecord();
               CopyUtils.copyProperties(sysReceiveRecord, sysReturnRecord);
               sysReturnRecord.setId(null);
               sysReturnRecord.setUserId(req.getUserId());
               sysReturnRecord.setDescription(req.getDescription());
               sysReturnRecord.setUsername(sysUserInfo.getUsername());
               sysReturnRecord.setCreateTime(now);

               // 审批人 -- 综合部资产管理员
               SysUser userInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("post_id", ParamsConstant.POST_GROUP_ASSET_MANAGER));
               sysReturnRecord.setFlowPath(userInfo.getId());
               sysReturnRecord.setStatus(ParamsConstant.AUDIT_STATUS_DEFAULT);
               sysReturnRecord.setUpdateTime(null);
               sysReturnRecord.setAssetStatus(req.getAssetStatus());
               sysReturnRecord.setUsedAge(usedAge + "年");

               //将资产领用那边的状态也改成归还中
               sysReceiveRecordMapper.update(null, new UpdateWrapper<SysReceiveRecord>().set("status",ParamsConstant.RECEIVE_STATUS_INTHEBACK).eq("id",req.getReceiveId()));
               sysReturnRecordMapper.insertSelective(sysReturnRecord);

               // todo 消息推送
              String accessToken = AppletUtils.getAccessToken();

           }catch (ChorBizException e){
               throw e;
           }catch (Exception e){
               throw new ChorBizException(AmErrorCode.SERVER_ERROR);
           }
    }

    @Override
    public Map<String, Object> list(SysReturnRecordListReq req){

        try{

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));

            if(req.getDepartmentId() == null){
                if (sysUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)) {
                    req.setDepartmentIds(null);
                } else {
                    String departmentIds = claims.get("fillArgs").toString();
                    req.setDepartmentIds(departmentIds);
                }
            }else{
                req.setDepartmentIds(req.getDepartmentId());
            }

            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysReturnRecordMapper.count(req);
            List<SysReturnRecordResp> list = sysReturnRecordMapper.list(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;

        }catch (Exception e){
            throw  new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public  Map<String, Object>  auditList(AuditListReq req){
        try{
            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysReturnRecordMapper.auditCount(req);
            List<SysReturnRecordResp> list = sysReturnRecordMapper.auditList(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;

        }catch (Exception e){
            throw  new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }


    @Override
    public SysReturnRecordResp find(Long id){
        try{
            //根据id查询
            SysReturnRecordResp sysReturnRecord = sysReturnRecordMapper.getById(id);

            if(sysReturnRecord==null){
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            List<SysAuditLog> sysAuditLogList = new ArrayList<>();

            // 将申请人信息写入
            SysAuditLog sysAuditLog = new SysAuditLog();
            sysAuditLog.setAuditName(sysReturnRecord.getUsername());  // 归还申请人姓名
            sysAuditLog.setAuditTime(sysReturnRecord.getCreateTime()); // 归还申请时间
            sysAuditLogList.add(sysAuditLog);

            //根据id查询审批流程
            List<SysAuditLog> sysAuditLogs = sysAuditLogMapper.selectList(new QueryWrapper<SysAuditLog>().eq("related_id", id).eq("type", ParamsConstant.AUDIT_TYPE_FOR_RETURN).orderByAsc("id"));
            sysAuditLogList.addAll(sysAuditLogs);
            sysReturnRecord.setSysAuditLogs(sysAuditLogList);
            return sysReturnRecord;
        }catch (ChorBizException e){
            throw e;
        }catch (Exception E){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void recall(Long id){
        try{

            //查询下是否存在
            SysReturnRecordResp info = sysReturnRecordMapper.getById(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }else if(!info.getStatus().equals(ParamsConstant.AUDIT_STATUS_DEFAULT)){
                throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
            }

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));

            // 判断是撤销人员是否是资产归还申请人
            if (!sysUserInfo.getId().equals(info.getUserId())) {
                throw new ChorBizException(AmErrorCode.AUTHORIZATION_ERROR);
            }

            SysReturnRecord record = new SysReturnRecord();
            record.setId(id);
            record.setStatus(ParamsConstant.AUDIT_STATUS_BACKOUT);
            record.setFlowPath(ParamsConstant.AUDIT_STATUS_DEFAULT);
            record.setUpdateTime(System.currentTimeMillis());
            sysReturnRecordMapper.updateByPrimaryKeySelective(record);

            //撤销，修改资产领用状态
            sysReceiveRecordMapper.update(null,new UpdateWrapper<SysReceiveRecord>()
                    .set("status",ParamsConstant.RECEIVE_STATUS_RECEIVE).eq("job_no",info.getJobNo()));
        }catch(ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirm(Long id){

        //查询归还记录
        SysReturnRecordResp info = sysReturnRecordMapper.getById(id);

        if (null == info) {
            throw new ChorBizException(AmErrorCode.NULL_FOUND);
        }else if(!info.getStatus().equals(ParamsConstant.AUDIT_STATUS_APPROVE) || !info.getFlowPath().equals(ParamsConstant.AUDIT_FLOWPAHT_FINISH)){
            throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
        }

        //将归还状态设为确认归还
        SysReturnRecord sysReturnRecord = new SysReturnRecord();
        sysReturnRecord.setId(id);
        sysReturnRecord.setStatus(ParamsConstant.CONFIRM_TO_RETURN_STATUS);
        sysReturnRecord.setUpdateTime(System.currentTimeMillis());
        sysReturnRecordMapper.updateByPrimaryKeySelective(sysReturnRecord);

        //将资产领用那边的状态也改成已归还
        sysReceiveRecordMapper.update(null,new UpdateWrapper<SysReceiveRecord>()
                .set("status", ParamsConstant.RECEIVE_STATUS_RETURNED).eq("job_no", info.getJobNo()));

        //将库存改为在库并释放
        sysAssetMapper.updateInventoryStatus(info.getAssetCode(),ParamsConstant.ASSET_INVENTORY_STATUS_IN,info.getNum(),System.currentTimeMillis(),ParamsConstant.ASSET_LOCKED_STATUS_OFF);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void audit(SysAuditLogReq req, Long id) {

        try {
            SysReturnRecord sysReturnRecordInfo = sysReturnRecordMapper.selectOne(new QueryWrapper<SysReturnRecord>()
                    .eq("id", id).and(query-> query.eq("status", ParamsConstant.AUDIT_STATUS_APPROVE).or().eq("status", ParamsConstant.AUDIT_STATUS_DEFAULT)));
            if (null == sysReturnRecordInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            SysUser auditUserInfo = sysUserMapper.selectById(req.getAuditBy());

            // 判断审核人员是否为综合部管理员
            if (!auditUserInfo.getPostId().equals(ParamsConstant.POST_GROUP_ASSET_MANAGER)) {
                throw new ChorBizException(AmErrorCode.AUTHORIZATION_ERROR);
            }

            // TODO 此级别审核完毕后，判断是否是最后一道审核，是则结束,更改flow_path为 -1 处理结束，否则消息推送至上级，且更改flow_path 为上级id
            long currentTimeMillis = System.currentTimeMillis();
            SysAuditLog sysAuditLog = new SysAuditLog();
            CopyUtils.copyProperties(req, sysAuditLog);
            sysAuditLog.setAuditTime(currentTimeMillis);
            sysAuditLog.setRelatedId(id);
            sysAuditLog.setType(ParamsConstant.AUDIT_TYPE_FOR_RETURN);
            sysAuditLog.setAuditName(auditUserInfo.getUsername());
            sysAuditLogMapper.insertSelective(sysAuditLog);

            // 消息审核状态变更为已审核
            sysMessageMapper.update(null, new UpdateWrapper<SysMessage>()
                    .set("audit_status", ParamsConstant.AUDIT_FLOWPAHT_FINISH)
                    .eq("type", ParamsConstant.AUDIT_TYPE_FOR_RETURN)
                    .eq("related_id", id)
                    .eq("to_user", req.getAuditBy()));

            sysReturnRecordInfo.setStatus(req.getAuditStatus());
            sysReturnRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
            sysReturnRecordMapper.updateById(sysReturnRecordInfo);

            if (req.getAuditStatus().equals(ParamsConstant.AUDIT_STATUS_REJECT)) {

                // 对应归还记录中状态变更为已领用
                sysReceiveRecordMapper.update(null,new UpdateWrapper<SysReceiveRecord>()
                        .set("status",ParamsConstant.RECEIVE_STATUS_RECEIVE).eq("job_no",sysReturnRecordInfo.getJobNo()));
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

            // 获取归还信息
            SysReturnRecord sysReturnRecordInfo = sysReturnRecordMapper.selectById(id);
            if (null == sysReturnRecordInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            List<FlowPathResp> flowPathRespList = new ArrayList<>();
            FlowPathResp returnUser = new FlowPathResp();
            returnUser.setUsername(sysReturnRecordInfo.getUsername());
            returnUser.setUserId(sysReturnRecordInfo.getUserId());
            returnUser.setPostName("申请人");
            flowPathRespList.add(returnUser); // 申请人

            // 获取综合部资产管理员信息
            SysUser assetUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                    .eq("department_id", ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT).eq("post_id", ParamsConstant.POST_GROUP_ASSET_MANAGER).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            FlowPathResp flowPathResp = getFlowPathResp(assetUserInfo, id);
            flowPathRespList.add(flowPathResp);

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
                SysReturnRecord sysReturnRecordInfo = sysReturnRecordMapper.selectOne(new QueryWrapper<SysReturnRecord>()
                        .eq("id", id).and(query-> query.eq("status", ParamsConstant.AUDIT_STATUS_APPROVE).or().eq("status", ParamsConstant.AUDIT_STATUS_DEFAULT)));
                if (null == sysReturnRecordInfo) {
                    throw new ChorBizException(AmErrorCode.NULL_FOUND);
                }

                SysUser auditUserInfo = sysUserMapper.selectById(auditBy);

                // 判断审核人员是否为综合部管理员
                if (!auditUserInfo.getPostId().equals(ParamsConstant.POST_GROUP_ASSET_MANAGER)) {
                    throw new ChorBizException(AmErrorCode.AUTHORIZATION_ERROR);
                }

                // 审批日志
                long currentTimeMillis = System.currentTimeMillis();
                SysAuditLog sysAuditLog = new SysAuditLog();
                CopyUtils.copyProperties(req, sysAuditLog);
                sysAuditLog.setAuditTime(currentTimeMillis);
                sysAuditLog.setRelatedId(id);
                sysAuditLog.setType(ParamsConstant.AUDIT_TYPE_FOR_RETURN);
                sysAuditLog.setAuditName(auditUserInfo.getUsername());
                sysAuditLog.setAuditBy(auditBy);
                sysAuditLogMapper.insertSelective(sysAuditLog);

                // 消息审核状态变更为已审核
                sysMessageMapper.update(null, new UpdateWrapper<SysMessage>()
                        .set("audit_status", ParamsConstant.AUDIT_FLOWPAHT_FINISH)
                        .eq("type", ParamsConstant.AUDIT_TYPE_FOR_RETURN)
                        .eq("related_id", id)
                        .eq("to_user", auditBy));

                sysReturnRecordInfo.setStatus(req.getAuditStatus());
                sysReturnRecordInfo.setFlowPath(ParamsConstant.AUDIT_FLOWPAHT_FINISH);
                sysReturnRecordMapper.updateById(sysReturnRecordInfo);

                if (req.getAuditStatus().equals(ParamsConstant.AUDIT_STATUS_REJECT)) {

                    // 对应归还记录中状态变更为已领用
                    sysReceiveRecordMapper.update(null,new UpdateWrapper<SysReceiveRecord>()
                            .set("status",ParamsConstant.RECEIVE_STATUS_RECEIVE).eq("job_no",sysReturnRecordInfo.getJobNo()));
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
                .eq("type", ParamsConstant.AUDIT_TYPE_FOR_RETURN).eq("related_id", relatedId).eq("audit_by", currentUser.getId()));

        if (null != sysAuditLog) {
            flowPathResp.setAuditStatus(sysAuditLog.getAuditStatus());
            flowPathResp.setReason(sysAuditLog.getReason());
        }

        return flowPathResp;
    }

}
