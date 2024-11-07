package com.cg.test.am.controller;

import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.vo.request.SysAssetListReq;
import com.cg.test.am.vo.request.SysAssetReq;
import com.cg.test.am.model.SysAsset;
import com.cg.test.am.service.SysAssetService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.response.SysAssetResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api(tags = "资产信息")
@RestController
@RequestMapping("/sysAsset")
public class SysAssetController {

    @Resource
    SysAssetService sysAssetService;

    @ApiOperation(value = "添加资产信息", notes = "管理端API")
    @PostMapping("/create")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙"),
            @ApiResponse(code = 400002, message = "该资产类型数量不能为多个")
    })
    public ChorResponse<Void> create(@RequestBody SysAssetReq req) {

        SysAsset sysAsset = new SysAsset();
        CopyUtils.copyProperties(req, sysAsset);
        sysAssetService.save(sysAsset);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "资产信息列表", notes = "管理端API", response = SysAssetResp.class)
    @GetMapping("/list")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    public ChorResponse<Map<String, Object>> list(@ModelAttribute SysAssetListReq req) {

        return ChorResponseUtils.success(sysAssetService.list(req));

    }

    @ApiOperation(value = "修改资产信息详情", notes = "管理端API")
    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙"),
            @ApiResponse(code = 400002, message = "该资产类型数量不能为多个")
    })
    public ChorResponse<Void> modify(@RequestBody SysAssetReq req, @PathVariable Long id) {

        sysAssetService.modify(req, id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "根据id查询资产信息详情", notes = "管理端API", response = SysAssetResp.class)
    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 300001, message = "修改对象不存在"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    public ChorResponse<SysAssetResp> find(@PathVariable Long id) {

        return ChorResponseUtils.success(sysAssetService.find(id));

    }


    @ApiOperation(value = "根据资产编号查询资产信息详情", notes = "小程序扫码使用", response = SysAssetResp.class)
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 300001, message = "查询对象不存在"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/getAssetDetailByAssetCode")
    public ChorResponse<SysAssetResp> getAssetDetailByAssetCode(@RequestParam(value = "assetCode") String assetCode) {

        return ChorResponseUtils.success(sysAssetService.getAssetDetailByAssetCode(assetCode));

    }


    @ApiOperation(value = "资产信息导出", notes = "departmentId部门id，不传查全部", httpMethod = "GET")
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse response, @RequestParam(value = "departmentId", required = false) String departmentId, @RequestParam(value = "token")String token){
        sysAssetService.downloadExcel(response,departmentId, token);
    }

}
