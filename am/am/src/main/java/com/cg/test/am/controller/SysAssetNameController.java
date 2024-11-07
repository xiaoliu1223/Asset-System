package com.cg.test.am.controller;


import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysAssetNameService;
import com.cg.test.am.vo.request.SysAssetNameReq;
import com.cg.test.am.vo.response.SysAssetNameResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "资产名称API")
@RestController
@RequestMapping("/sysAssetName")
public class SysAssetNameController {

    @Resource
    SysAssetNameService sysAssetNameServiceImpl;

    @ApiOperation(value = "添加资产名称", notes = "管理端API")
    @PostMapping("/create")
    public ChorResponse<Void> create(@RequestBody SysAssetNameReq req) {

        sysAssetNameServiceImpl.save(req);
        return ChorResponseUtils.success();

    }


    @ApiOperation(value = "资产名称列表", notes = "管理端API")
    @GetMapping("/list")
    public ChorResponse<List<SysAssetNameResp>> list() {

        List<SysAssetNameResp> resp = sysAssetNameServiceImpl.list();
        return ChorResponseUtils.success(resp);

    }


    @ApiOperation(value = "删除资产名称", notes = "管理端API")
    @DeleteMapping("/{id}")
    public ChorResponse<Void> remove(@PathVariable  Long id) {

        sysAssetNameServiceImpl.remove(id);
        return ChorResponseUtils.success();

    }
}
