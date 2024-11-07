package com.cg.test.am.service;

import com.cg.test.am.model.SysApplicationRecord;
import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysApplicationRecordResp;

import java.util.List;
import java.util.Map;

public interface SysApplicationRecordService {

    /**
     * 资产申请
     * @param sysApplicationRecord
     */
    String save(SysApplicationRecord sysApplicationRecord);

    /**
     * 资产列表
     * @param sysApplicationRecordListReq
     */
    Map<String, Object> list(SysApplicationRecordListReq sysApplicationRecordListReq);

    /**
     * 审核资产列表
     * @param sysApplicationRecordAuditListReq
     */
    Map<String, Object> auditList(AuditListReq sysApplicationRecordAuditListReq);

    /**
     * 取得资产详情
     * @param id
     * @return
     */
    SysApplicationRecordResp find(Long id);

    /**
     * 修改资产信息
     * @param req
     * @param id
     */
    void modify(SysApplicationRecordReq req, Long id);

    /**
     * 撤销资产信息
     * @param id
     */
    void undo(Long id);

    /**
     * 资产申请审核
     * @param req
     * @param id
     */
    void audit(SysAuditLogReq req, Long id);


    /**
     * 获取资产申请的审批流
     * @param id
     * @return
     */
    List<FlowPathResp> auditFlowList(Long id);

    /**
     * 批量审批
     * @param req
     * @param auditBy
     */
    void batchAudit(SysAuditLogBatchReq req, Integer auditBy);

    List<SysApplicationRecordResp> getByRelateJobNo(String relateJobNo);
}
