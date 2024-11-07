package com.cg.test.am.controller;


import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysUnitService;
import com.cg.test.am.vo.request.SysUnitReq;
import com.cg.test.am.vo.response.SysUnitResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "计量单位API")
@RestController
@RequestMapping("/sysUnit")
public class SysUnitController {

    @Resource
    SysUnitService sysUnitServiceImpl;

    @ApiOperation(value = "添加计量单位", notes = "管理端API")
    @PostMapping("/create")
    public ChorResponse<Void> create(@RequestBody SysUnitReq req) {

        sysUnitServiceImpl.save(req);
        return ChorResponseUtils.success();

    }


    @ApiOperation(value = "计量单位列表", notes = "管理端API")
    @GetMapping("/list")
    public ChorResponse<List<SysUnitResp>> list() {

        List<SysUnitResp> resp = sysUnitServiceImpl.list();
        return ChorResponseUtils.success(resp);

    }


    @ApiOperation(value = "删除计量单位", notes = "管理端API")
    @DeleteMapping("/{id}")
    public ChorResponse<Void> remove(@PathVariable  Long id) {

        sysUnitServiceImpl.remove(id);
        return ChorResponseUtils.success();

    }





}
