package com.cg.test.am.service;

import com.cg.test.am.response.core.ChorPageResponse;
import com.cg.test.am.vo.request.SysAuditLogReq;
import com.cg.test.am.vo.request.SysAuditLogAuditListReq;
import com.cg.test.am.vo.request.SysAuditLogListReq;
import com.cg.test.am.vo.response.SysAuditLogResp;

import java.util.Map;

public interface SysAuditLogService {

    /**
     * 生成审核日志
     * @param req
     */
    void save(SysAuditLogReq req);

    /**
     * 某申请的审核记录信息列表
     * @param req
     * @return
     */
    ChorPageResponse<SysAuditLogResp> list(SysAuditLogListReq req);

    /**
     * 审核记录的详细列表
     */
    Map<String, Object> auditList(SysAuditLogAuditListReq req);
}
