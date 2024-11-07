package com.cg.test.am.controller;

import com.cg.test.am.annotation.CheckLock;
import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysChargeOffRecordService;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysChargeOffRecordResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "资产核销")
@RestController
@RequestMapping("/sysChargeOffRecord")
public class SysChargeOffRecordController {

    @Resource
    SysChargeOffRecordService sysChargeOffRecordService;

    @Resource
    HttpServletRequest httpServletRequest;

    @ApiOperation(value = "核销申请", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @CheckLock
    @PostMapping("/create")
    public ChorResponse<Void> create(@RequestBody SysChargeOffRecordReq req) {


        sysChargeOffRecordService.save(req);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "列表", notes = "管理端API", response = SysChargeOffRecordResp.class)
    @GetMapping("/list")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    public ChorResponse<Map<String, Object>> list(@ModelAttribute SysChargeOffRecordListReq req) {

        return ChorResponseUtils.success(sysChargeOffRecordService.list(req));

    }

    @ApiOperation(value = "审核核销列表", notes = "管理端API", response = SysChargeOffRecordResp.class)
    @GetMapping("/auditList")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    public ChorResponse<Map<String, Object>> auditList(@ModelAttribute AuditListReq req) {

        return ChorResponseUtils.success(sysChargeOffRecordService.auditList(req));

    }

    @ApiOperation(value = "根据id查看审批详情", notes = "管理端API")
    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 000000, message = "修改对象不存在"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    public ChorResponse<SysChargeOffRecordResp> find(@PathVariable Long id) {

        return ChorResponseUtils.success(sysChargeOffRecordService.find(id));

    }

    @ApiOperation(value = "撤回", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙"),
            @ApiResponse(code = 300001, message = "找不到对象"),
            @ApiResponse(code = 600002, message = "此状态数据不能改变")
    })
    @GetMapping("/recall/{id}")
    public ChorResponse<Void> recall(@PathVariable Long id) {

        sysChargeOffRecordService.recall(id);
        return ChorResponseUtils.success();

    }

//    @Deprecated
//    @ApiOperation(value = "确认核销 - old", notes = "管理端API")
//    @GetMapping("/cancel/{id}")
//    @ApiResponses({
//            @ApiResponse(code = 000000, message = "success"),
//            @ApiResponse(code = 500001, message = "系统繁忙"),
//            @ApiResponse(code = 300001, message = "找不到对象"),
//            @ApiResponse(code = 600002, message = "此状态数据不能改变")
//    })
//    public ChorResponse<Void> cancel(@PathVariable Long id) {
//
//        sysChargeOffRecordService.cancel(id);
//        return ChorResponseUtils.success();
//    }

    @ApiOperation(value = "确认核销", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙"),
            @ApiResponse(code = 300001, message = "找不到对象"),
            @ApiResponse(code = 600002, message = "此状态数据不能改变")
    })
    @CheckLock
    @PostMapping("/confirm/{id}")
    public ChorResponse<Void> confirm(@RequestBody SysChargeOffRecordConfirmReq req, @PathVariable Long id) {

        sysChargeOffRecordService.confirm(req, id);
        return ChorResponseUtils.success();
    }


    @ApiOperation(value = "审核--资产核销", notes = "web、applet API")
    @ApiResponses({
            @ApiResponse(code = 000001, message = "success"),
            @ApiResponse(code = 300001, message = "审核对象不存在、或已经撤销"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PostMapping("/audit/{id}")
    public ChorResponse<Void> audit(@RequestBody SysAuditLogReq req, @PathVariable Long id) {

        sysChargeOffRecordService.audit(req, id);
        return ChorResponseUtils.success();
    }

    @ApiOperation(value = "批量审核--资产核销", notes = "web、applet API")
    @ApiResponses({
            @ApiResponse(code = 000001, message = "success"),
            @ApiResponse(code = 300001, message = "审核对象不存在、或已经撤销"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PostMapping("/batchAudit")
    public ChorResponse<Void> batchAudit(@RequestBody SysAuditLogBatchReq req) {

        String token = httpServletRequest.getHeader("Authorization");
        Integer auditBy = JwtUtil.getUserIdByToken(token);
        sysChargeOffRecordService.batchAudit(req, auditBy);
        return ChorResponseUtils.success();
    }


    @ApiOperation(value = "查询资产核销审批流列表", notes = "applet API", response = FlowPathResp.class)
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 300001, message = "查询对象不存在、或已经撤销核销申请"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/audit/flow/{id}")
    public ChorResponse<List<FlowPathResp>> auditFlowList(@PathVariable Long id) {

        List<FlowPathResp> resp = sysChargeOffRecordService.auditFlowList(id);
        return ChorResponseUtils.success(resp);
    }
}
