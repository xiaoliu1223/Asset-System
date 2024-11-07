package com.cg.test.am.controller;

import com.cg.test.am.response.core.ChorPageResponse;
import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysAuditLogService;
import com.cg.test.am.vo.request.SysAuditLogListReq;
import com.cg.test.am.vo.response.SysAuditLogAuditListResp;
import com.cg.test.am.vo.request.SysAuditLogAuditListReq;
import com.cg.test.am.vo.response.SysAuditLogResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "审核日志")
@RestController
@RequestMapping("/audit")
public class SysAuditLogController {


    @Resource
    SysAuditLogService sysAuditLogServiceImpl;

//    @ApiOperation(value = "审核", notes = "没用")
//    @ApiResponses({
//            @ApiResponse(code = 000000, message = "success"),
//            @ApiResponse(code = 300001, message = "审核对象不存在"),
//            @ApiResponse(code = 500001, message = "系统繁忙")
//    })
//    @Deprecated
//    @PostMapping("/add")
//    public ChorResponse<Void> audit (@RequestBody SysAuditLogReq req) {
//
//        sysAuditLogServiceImpl.save(req);
//        return ChorResponseUtils.success();
//    }

    @ApiOperation(value = "审核列表", notes = "web、applet", response = SysAuditLogResp.class)
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping()
    public ChorPageResponse<SysAuditLogResp> list(@ModelAttribute SysAuditLogListReq req) {

        return sysAuditLogServiceImpl.list(req);

    }

    @ApiOperation(value = "审核详细记录列表", notes = "web、applet", response = SysAuditLogAuditListResp.class)
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/auditList")
    public ChorResponse<Map<String, Object>> auditList(@ModelAttribute SysAuditLogAuditListReq req) {

            return ChorResponseUtils.success(sysAuditLogServiceImpl.auditList(req));

    }
}
