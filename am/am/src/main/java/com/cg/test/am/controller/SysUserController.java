package com.cg.test.am.controller;

import com.cg.test.am.model.SysUser;
import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.vo.request.SysUserUpdateReq;
import com.cg.test.am.vo.request.ChangePasswordReq;
import com.cg.test.am.vo.request.SysUserListReq;
import com.cg.test.am.vo.response.SysUserResp;
import com.cg.test.am.service.SysDepartmentService;
import com.cg.test.am.service.SysUserService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.request.SysUserAddReq;
import com.cg.test.am.vo.response.SysTree;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "用户")
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Resource
    SysUserService sysUserService;
    @Resource
    SysDepartmentService sysDepartmentService;

    @ApiOperation(value = "添加用户", notes = "管理端API")
    @PostMapping("/create")
    public ChorResponse<Void> create(@RequestBody SysUserAddReq req) {

        SysUser sysUser = new SysUser();
        CopyUtils.copyProperties(req, sysUser);
        sysUserService.save(sysUser);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "用户列表", notes = "管理端API", response = SysUserResp.class)
    @GetMapping("/list")
    public ChorResponse<Map<String, Object>> list(@ModelAttribute SysUserListReq req) {

        return ChorResponseUtils.success(sysUserService.list(req));

    }

    @ApiOperation(value = "根据id查询详情", notes = "管理端API", response = SysUserResp.class)
    @GetMapping("/{id}")
    public ChorResponse<SysUserResp> find(@PathVariable Integer id) {

        return ChorResponseUtils.success(sysUserService.find(id));

    }

    @ApiOperation(value = "修改用户详情", notes = "管理端API")
    @PutMapping("/{id}")
    public ChorResponse<Void> modify(@RequestBody SysUserUpdateReq req, @PathVariable Integer id) {

        sysUserService.modify(req, id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "删除用户", notes = "管理端API")
    @DeleteMapping("/{id}")
    public ChorResponse<Void> remove(@PathVariable Integer id) {

        sysUserService.remove(id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "用户密码初始化", notes = "管理端API")
    @PutMapping("/initial/{id}")
    public ChorResponse<Void> initial(@PathVariable Integer id) {

        sysUserService.initial(id);
        return ChorResponseUtils.success();

    }

    @ApiOperation(value = "根据用户id获取部门列表(修改回显)", notes = "管理端API")
    @GetMapping("/treeList")
    public ChorResponse<List<SysTree>> treeList(Integer id) {

        return ChorResponseUtils.success(sysDepartmentService.treeList(id));
    }

    @ApiOperation(value = "修改密码", notes = "管理端API")
    @PutMapping("/changePassword")
    public ChorResponse<Void> changePassword(@RequestBody ChangePasswordReq req) {

        sysUserService.changePassword(req);
        return ChorResponseUtils.success();

    }

}
