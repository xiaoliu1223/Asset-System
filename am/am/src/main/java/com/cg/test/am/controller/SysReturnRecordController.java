package com.cg.test.am.controller;

import com.cg.test.am.annotation.CheckLock;
import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysReturnRecordService;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.vo.response.SysReturnRecordResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "资产归还")
@RestController
@RequestMapping("/sysReturnRecord")
public class SysReturnRecordController {

    @Resource
    SysReturnRecordService sysReturnRecordService;

    @Resource
    HttpServletRequest httpServletRequest;


    @ApiOperation(value = "新增", notes = "管理端API")
    @PostMapping("/create")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 300001, message = "找不到对象"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    public ChorResponse<Void> create(@RequestBody SysReturnRecordReq req) {

        sysReturnRecordService.save(req);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "列表", notes = "管理端API", response = SysReturnRecordResp.class)
    @GetMapping("/list")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    public ChorResponse<Map<String, Object>> list(@ModelAttribute SysReturnRecordListReq req) {

        return ChorResponseUtils.success(sysReturnRecordService.list(req));

    }

    @ApiOperation(value = "审核归还列表", notes = "管理端API", response = SysReturnRecordResp.class)
    @GetMapping("/auditList")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    public ChorResponse<Map<String, Object>> auditList(@ModelAttribute AuditListReq req) {

        return ChorResponseUtils.success(sysReturnRecordService.auditList(req));

    }

    @ApiOperation(value = "根据id查看审批详情", notes = "管理端API")
    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 000000, message = "修改对象不存在"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    public ChorResponse<SysReturnRecordResp> find(@PathVariable Long id) {

        return ChorResponseUtils.success(sysReturnRecordService.find(id));

    }

    @ApiOperation(value = "撤回", notes = "管理端API")
    @GetMapping("/recall/{id}")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙"),
            @ApiResponse(code = 300001, message = "找不到对象"),
            @ApiResponse(code = 600002, message = "此状态数据不能改变")
    })
    public ChorResponse<Void> recall(@PathVariable Long id) {

        sysReturnRecordService.recall(id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "确认归还", notes = "管理端API")
    @GetMapping("/back/{id}")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 300001, message = "找不到对象"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @CheckLock
    public ChorResponse<Void> commitBack(@PathVariable Long id) {

        sysReturnRecordService.confirm(id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "审核--资产归还", notes = "web、applet API")
    @ApiResponses({
            @ApiResponse(code = 000001, message = "success"),
            @ApiResponse(code = 300001, message = "审核对象不存在、或已经撤销"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PostMapping("/audit/{id}")
    public ChorResponse<Void> audit(@RequestBody SysAuditLogReq req, @PathVariable Long id) {

        sysReturnRecordService.audit(req, id);
        return ChorResponseUtils.success();
    }


    @ApiOperation(value = "批量审核--资产归还", notes = "web、applet API")
    @ApiResponses({
            @ApiResponse(code = 000001, message = "success"),
            @ApiResponse(code = 300001, message = "审核对象不存在、或已经撤销"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PostMapping("/batchAudit")
    public ChorResponse<Void> batchAudit(@RequestBody SysAuditLogBatchReq req) {

        String token = httpServletRequest.getHeader("Authorization");
        Integer auditBy = JwtUtil.getUserIdByToken(token);
        sysReturnRecordService.batchAudit(req, auditBy);
        return ChorResponseUtils.success();
    }


    @ApiOperation(value = "查询资产归还审批流列表", notes = "applet API", response = FlowPathResp.class)
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 300001, message = "查询对象不存在、或已经撤销申请"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/audit/flow/{id}")
    public ChorResponse<List<FlowPathResp>> auditFlowList(@PathVariable Long id) {

        List<FlowPathResp> resp = sysReturnRecordService.auditFlowList(id);
        return ChorResponseUtils.success(resp);

    }
}
