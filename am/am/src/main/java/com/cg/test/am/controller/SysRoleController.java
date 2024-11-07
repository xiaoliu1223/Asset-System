package com.cg.test.am.controller;

import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysPermissionService;
import com.cg.test.am.vo.response.GeneralResp;
import com.cg.test.am.model.SysRole;
import com.cg.test.am.service.SysRoleService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.request.SysRoleListReq;
import com.cg.test.am.vo.request.SysRoleReq;
import com.cg.test.am.vo.response.SysRoleResp;
import com.cg.test.am.vo.response.TreePermissionResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "角色API")
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Resource
    SysRoleService sysRoleService;
    @Resource
    SysPermissionService sysPermissionServiceImpl;


    @ApiOperation(value = "添加角色", notes = "管理端API")
    @PostMapping("/create")
    public ChorResponse<Void> create(@RequestBody SysRoleReq req) {

        SysRole sysRole = new SysRole();
        CopyUtils.copyProperties(req, sysRole);
        sysRoleService.save(sysRole);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "角色列表", notes = "管理端API")
    @GetMapping("/list")
    public ChorResponse<Map<String, Object>> list(@ModelAttribute SysRoleListReq req) {

        return ChorResponseUtils.success(sysRoleService.list(req));

    }

    @ApiOperation(value = "根据id查询角色详情", notes = "管理端API")
    @GetMapping("/{id}")
    public ChorResponse<SysRoleResp> find(@PathVariable Long id) {

        return ChorResponseUtils.success(sysRoleService.find(id));

    }

    @ApiOperation(value = "修改角色详情", notes = "管理端API")
    @PutMapping("/{id}")
    public ChorResponse<Void> modify(@RequestBody SysRoleReq req, @PathVariable Long id) {

        sysRoleService.modify(req, id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "删除角色类别", notes = "管理端API")
    @DeleteMapping("/{id}")
    public ChorResponse<Void> remove(@PathVariable Long id) {

        sysRoleService.remove(id);
        return ChorResponseUtils.success();

    }

//    @ApiOperation(value = "根据角色id获取拥有的权限", notes = "管理端API")
//    @GetMapping(params = "roleId")
//    public ChorResponse<List<GeneralResp>> permissions(Long roleId) {
//        return ChorResponseUtils.success(sysRoleService.listByRoleId(roleId));
//    }

    @ApiOperation(value = "根据角色id获取权限列表(修改回显)", notes = "管理端API")
    @GetMapping("/treeList")
    public ChorResponse<List<TreePermissionResp>> treeList(@RequestParam(value = "roleId") Long roleId) {

        List<TreePermissionResp> resp = sysPermissionServiceImpl.treeList(roleId);
        return ChorResponseUtils.success(resp);
    }

    @ApiOperation(value = "获取全部角色", notes = "创建用户分配角色使用，管理端API")
    @GetMapping("/allList")
    public ChorResponse<List<GeneralResp>> allList() {
        return ChorResponseUtils.success(sysRoleService.allList());
    }

    @ApiOperation(value = "根据用户id获取拥有的角色", notes = "管理端API")
    @GetMapping(params = "userId")
    public ChorResponse<List<GeneralResp>> roles(Long userId) {

        return ChorResponseUtils.success(sysRoleService.listByUserId(userId));

    }


}
