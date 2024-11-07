package com.cg.test.am.service;

import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysChargeOffRecordResp;

import java.util.List;
import java.util.Map;

public interface SysChargeOffRecordService {

    /**
     * 核销申请
     */
    void save(SysChargeOffRecordReq req);

    /**
     * 核销列表
     */
    Map<String,Object> list(SysChargeOffRecordListReq req);

    /**
     * 审核核销列表
     */
    Map<String,Object> auditList(AuditListReq req);
    /**
     * 查看审批详情
     */
    SysChargeOffRecordResp find(Long id);

    /**
     * 撤回
     */
    void recall(Long id);

//    /**
//     * 核销
//     */
//    void cancel(Long id);

    /**
     * 审核--核销
     * @param req
     * @param id
     */
    void audit(SysAuditLogReq req, Long id);

    /**
     * 确认核销
     * @param req
     * @param id
     */
    void confirm(SysChargeOffRecordConfirmReq req, Long id);

    /**
     * 资产核销审批流列表
     * @param id
     * @return
     */
    List<FlowPathResp> auditFlowList(Long id);

    /**
     * 资产核销批量审批
     * @param req
     * @param auditBy 审核人id
     */
    void batchAudit(SysAuditLogBatchReq req, Integer auditBy);
}
