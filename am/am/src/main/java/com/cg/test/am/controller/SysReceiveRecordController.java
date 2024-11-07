package com.cg.test.am.controller;

import com.cg.test.am.annotation.CheckLock;
import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysReceiveRecordService;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysReceiveRecordListResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "资产领用")
@RestController
@RequestMapping("/sysReceiveRecord")
public class SysReceiveRecordController {

    @Resource
    SysReceiveRecordService sysReceiveRecordService;

    @Resource
    HttpServletRequest httpServletRequest;

    @ApiOperation(value = "新增资产领用", notes = "管理端API")

    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @CheckLock
    @PostMapping("/create")
    public synchronized ChorResponse<Void> create(@RequestBody SysReceiveRecordReq req) {
        sysReceiveRecordService.save(req);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "资产领用列表",notes = "管理端API", response = SysReceiveRecordListResp.class)
    @GetMapping("/list")
    public ChorResponse<Map<String,Object>> list(@ModelAttribute SysReceiveRecordListReq req){
        return new ChorResponse<>(sysReceiveRecordService.list(req));
    }

    @ApiOperation(value = "资产领用审核列表",notes = "管理端API", response = SysReceiveRecordListResp.class)
    @GetMapping("/auditList")
    public ChorResponse<Map<String,Object>> auditList(@ModelAttribute AuditListReq req){
        return new ChorResponse<>(sysReceiveRecordService.auditList(req));
    }


    @ApiOperation(value = "根据id查询资产领用详情", notes = "管理端API")

    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 300001, message = "修改对象不存在"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/{id}")
    public ChorResponse<SysReceiveRecordListResp> find(@PathVariable Long id) {

        return ChorResponseUtils.success(sysReceiveRecordService.find(id));

    }

    @ApiOperation(value = "撤回", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/recall/{id}")
    public ChorResponse<Void> recall(@PathVariable Long id) {

        sysReceiveRecordService.recall(id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "确认领用", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @CheckLock
    @GetMapping("/confirmReceive/{id}")
    public ChorResponse<Void> confirmReceive(@PathVariable Long id) {

        sysReceiveRecordService.confirmReceive(id);
        return ChorResponseUtils.success();

    }


    @ApiOperation(value = "审核--资产领用", notes = "web、applet API")
    @ApiResponses({
            @ApiResponse(code = 000001, message = "success"),
            @ApiResponse(code = 300001, message = "审核对象不存在、或已经撤销"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PostMapping("audit/{id}")
    public ChorResponse<Void> audit(@RequestBody SysAuditLogReq req, @PathVariable Long id) {

        sysReceiveRecordService.audit(req, id);
        return ChorResponseUtils.success();
    }


    @ApiOperation(value = "批量审核--资产领用", notes = "web、applet API")
    @ApiResponses({
            @ApiResponse(code = 000001, message = "success"),
            @ApiResponse(code = 300001, message = "审核对象不存在、或已经撤销"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PostMapping("/batchAudit")
    public ChorResponse<Void> batchAudit(@RequestBody SysAuditLogBatchReq req) {

        String token = httpServletRequest.getHeader("Authorization");
        Integer auditBy = JwtUtil.getUserIdByToken(token);

        sysReceiveRecordService.batchAudit(req, auditBy);
        return ChorResponseUtils.success();
    }



    @ApiOperation(value = "查询资产领用审批流列表", notes = "applet API", response = FlowPathResp.class)
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 300001, message = "查询对象不存在、或已经撤销申请"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/audit/flow/{id}")
    public ChorResponse<List<FlowPathResp>> auditFlowList(@PathVariable Long id) {

        List<FlowPathResp> resp = sysReceiveRecordService.auditFlowList(id);
        return ChorResponseUtils.success(resp);

    }
}
