package com.cg.test.am.service;

import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysReturnRecordResp;

import java.util.List;
import java.util.Map;

public interface SysReturnRecordService {

    /**
     * 新增资产归还
     */
    void save(SysReturnRecordReq req);


    /**
     * 资产归还列表
     */
    Map<String, Object> list(SysReturnRecordListReq req);

    /**
     * 审核资产归还列表
     */
    Map<String, Object>  auditList(AuditListReq req);


    /**
     * 根据id查询
     */
    SysReturnRecordResp find(Long id);

    /**
     * 撤回
     */
    void recall(Long id);

    /**
     * 确认归还
     */
    void confirm(Long id);

    /**
     * 审核
     * @param req
     * @param id
     */
    void audit(SysAuditLogReq req, Long id);

    /**
     * 资产归还审批流
     * @param id
     * @return
     */
    List<FlowPathResp> auditFlowList(Long id);

    /**
     * 资产归还批量审批
     * @param req
     * @param auditBy 审批人
     */
    void batchAudit(SysAuditLogBatchReq req, Integer auditBy);
}
