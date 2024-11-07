package com.cg.test.am.controller;

import com.cg.test.am.annotation.CheckLock;
import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysPurchaseRecordService;
import com.cg.test.am.vo.request.SysPurchaseRecordListReq;
import com.cg.test.am.vo.request.SysPurchaseConfirmReq;
import com.cg.test.am.vo.response.SysPurchaseRecordListResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "资产采购")
@RestController
@RequestMapping("/sysPurchaseRecord")
public class SysPurchaseRecordController {

    @Resource
    SysPurchaseRecordService sysPurchaseRecordService;

    @ApiOperation(value = "采购列表",notes = "管理端API", response = SysPurchaseRecordListResp.class)
    @GetMapping("/list")
    public ChorResponse<Map<String,Object>> list(@ModelAttribute SysPurchaseRecordListReq req){
        return ChorResponseUtils.success(sysPurchaseRecordService.list(req));
    }

    @ApiOperation(value = "确认采购", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000001, message = "success"),
            @ApiResponse(code = 300001, message = "确认采购对象不存在"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @CheckLock
    @PostMapping("/confirm/{id}")
    public ChorResponse<Void> confirm(@RequestBody SysPurchaseConfirmReq req, @PathVariable Long id) {

        sysPurchaseRecordService.confirm(req, id);
        return ChorResponseUtils.success();
    }
}
