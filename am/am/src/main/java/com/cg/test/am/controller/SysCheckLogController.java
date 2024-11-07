package com.cg.test.am.controller;

import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.vo.request.SysCheckLogListReq;
import com.cg.test.am.vo.request.SysCheckLogReq;
import com.cg.test.am.vo.response.SysCheckLogListResp;
import com.cg.test.am.service.SysCheckLogService;
import com.cg.test.am.vo.response.SysCheckLogResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "盘点日志")
@RestController
@RequestMapping("/sysCheckLog")
public class SysCheckLogController {

    @Resource
    SysCheckLogService sysCheckLogService;

    @ApiOperation(value = "列表", notes = "管理端API", response = SysCheckLogListResp.class)
    @GetMapping("/list")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    public ChorResponse<Map<String, Object>> list(@ModelAttribute SysCheckLogListReq req) {

        return ChorResponseUtils.success(sysCheckLogService.list(req));

    }

    @ApiOperation(value = "添加盘点日志", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 600003, message = "请勿重复盘点"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PostMapping("/create")
    public ChorResponse<Void> create(@RequestBody SysCheckLogReq req) {

        sysCheckLogService.save(req);
        return ChorResponseUtils.success();
    }


    @ApiOperation(value = "根据资产编码查看盘点日志详情", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/detail")
    public ChorResponse<List<SysCheckLogResp>> detail(@RequestParam(value = "assetCode") String assetCode) {

        return ChorResponseUtils.success(sysCheckLogService.detail(assetCode));

    }

}
