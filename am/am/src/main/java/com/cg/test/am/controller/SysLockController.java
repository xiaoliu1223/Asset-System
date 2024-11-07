package com.cg.test.am.controller;

import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysLockService;
import com.cg.test.am.vo.request.SysLockReq;
import com.cg.test.am.vo.response.SysLockResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "盘点锁库API")
@RestController
@RequestMapping("/sysLock")
public class SysLockController {


    @Resource
    SysLockService sysLockServiceImpl;

    @ApiOperation(value = "资产盘点开关", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PutMapping("/switchAssetLock")
    public ChorResponse<Void> switchAssetLock(@RequestBody SysLockReq req) {
        sysLockServiceImpl.switchAssetLock(req);
        return ChorResponseUtils.success();
    }

    @ApiOperation(value = "资产盘点开关状态", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/getLockStatus")
    public ChorResponse<SysLockResp> getLockStatus() {
        SysLockResp resp = sysLockServiceImpl.getLockStatus();
        return ChorResponseUtils.success(resp);
    }
}
