package com.cg.test.am.service;

import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysReceiveRecordListResp;

import java.util.List;
import java.util.Map;

public interface SysReceiveRecordService {

    /**
     * 资产领用列表
     * @param req
     * @return
     */
    Map<String,Object> list(SysReceiveRecordListReq req);

    /**
     * 审核资产领用列表
     * @param req
     * @return
     */
    Map<String,Object> auditList(AuditListReq req);

    /**
     * 新增资产领用
     */
    void save(SysReceiveRecordReq sysRecordRecord);

    /**
     * 查询资产领用详情
     */
    SysReceiveRecordListResp find(Long id);

    /**
     * 撤回资产领用
     */
    void recall(Long id);

    /**
     * 资产领用
     */
    void confirmReceive(Long id);

    /**
     * 资产领用审核
     * @param req
     * @param id
     */
    void audit(SysAuditLogReq req, Long id);

    /**
     * 资产领用审批流
     * @param id
     * @return
     */
    List<FlowPathResp> auditFlowList(Long id);

    /**
     * 批量审核资产领用
     * @param req
     * @param auditBy  审核人
     */
    void batchAudit(SysAuditLogBatchReq req, Integer auditBy);
}
