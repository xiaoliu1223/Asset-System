package com.cg.test.am.controller;


import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysPermissionService;
import com.cg.test.am.model.SysPermission;
import com.cg.test.am.vo.request.SysPermissionReq;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.response.SysPermissionResp;
import com.cg.test.am.vo.response.TreePermissionResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "权限管理")
@RestController
@RequestMapping("/permission")
public class SysPermissionController {

    @Resource
    SysPermissionService sysPermissionServiceImpl;


    @ApiOperation(value = "权限树状列表", notes = "管理端API", response = TreePermissionResp.class)
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/tree")
    public ChorResponse<List<TreePermissionResp>> treeList() {

        List<TreePermissionResp> resp = sysPermissionServiceImpl.treeList(ParamsConstant.DEL_FLAG_DEFAULT_VALUE);
        return ChorResponseUtils.success(resp);
    }

    @ApiOperation(value = "添加权限信息", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PostMapping("/create")
    public ChorResponse<Void> create(@RequestBody SysPermissionReq req) {

        SysPermission sysPermission = new SysPermission();
        CopyUtils.copyProperties(req, sysPermission);
        sysPermissionServiceImpl.save(sysPermission);
        return ChorResponseUtils.success();
    }


    @ApiOperation(value = "删除权限信息", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 000000, message = "删除对象不存在"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @DeleteMapping("/{id}")
    public ChorResponse<Void> remove(@PathVariable Long id) {

        sysPermissionServiceImpl.remove(id);
        return ChorResponseUtils.success();
    }

    @ApiOperation(value = "修改权限信息", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 000000, message = "修改对象不存在"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @PutMapping("/{id}")
    public ChorResponse<Void> modify(@RequestBody SysPermissionReq req, @PathVariable Long id) {

        sysPermissionServiceImpl.modify(req, id);
        return ChorResponseUtils.success();
    }


    @ApiOperation(value = "id获取权限详情", notes = "管理端API")
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 000000, message = "修改对象不存在"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/{id}")
    public ChorResponse<SysPermissionResp> find(@PathVariable Long id) {

        SysPermissionResp resp = sysPermissionServiceImpl.find(id);
        return ChorResponseUtils.success(resp);
    }

}
