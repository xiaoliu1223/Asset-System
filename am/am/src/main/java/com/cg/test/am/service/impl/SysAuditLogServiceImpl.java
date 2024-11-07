package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.*;
import com.cg.test.am.model.SysAuditLog;
import com.cg.test.am.response.core.ChorPageResponse;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.response.utils.ChorPageResponseUtils;
import com.cg.test.am.service.SysAuditLogService;
import com.cg.test.am.utils.BeanUtils;
import com.cg.test.am.utils.CollectionUtils;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.request.SysAuditLogAuditListReq;
import com.cg.test.am.vo.request.SysAuditLogListReq;
import com.cg.test.am.vo.request.SysAuditLogReq;
import com.cg.test.am.vo.response.SysAuditLogAuditListResp;
import com.cg.test.am.vo.response.SysAuditLogResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysAuditLogServiceImpl implements SysAuditLogService {

    @Resource
    SysAuditLogMapper sysAuditLogMapper;

    @Resource
    SysApplicationRecordMapper sysApplicationRecordMapper;

    @Resource
    SysReceiveRecordMapper sysReceiveRecordMapper;

    @Resource
    SysReturnRecordMapper sysReturnRecordMapper;

    @Resource
    SysChargeOffRecordMapper sysChargeOffRecordMapper;


    @Override
    public void save(SysAuditLogReq req) {

        try {

            long currentTimeMillis = System.currentTimeMillis();
            SysAuditLog sysAuditLog = new SysAuditLog();
            CopyUtils.copyProperties(req, sysAuditLog);
            sysAuditLog.setAuditTime(currentTimeMillis);
            sysAuditLogMapper.insertSelective(sysAuditLog);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public ChorPageResponse<SysAuditLogResp> list(SysAuditLogListReq req) {
        try {

            Page<SysAuditLog> page = new Page<>(req.getCurrent(), req.getLimit());

            QueryWrapper<SysAuditLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type", req.getType()).eq("related_id", req.getRelatedId());

            IPage<SysAuditLog> resp = sysAuditLogMapper.selectPage(page, queryWrapper);
            if (CollectionUtils.isEmpty(resp.getRecords())) {
                return ChorPageResponseUtils.success(new ArrayList<>());
            }

            List<SysAuditLogResp> sysAuditLogRespList = BeanUtils.toList(resp.getRecords(), SysAuditLogResp.class);
            return ChorPageResponseUtils.success((int)resp.getSize(), (int)resp.getCurrent(), resp.getTotal(), sysAuditLogRespList);

        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public Map<String, Object>  auditList(SysAuditLogAuditListReq req){
        try {

            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysAuditLogMapper.count(req);
            List<SysAuditLogAuditListResp> list = sysAuditLogMapper.list(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;

        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }
}
