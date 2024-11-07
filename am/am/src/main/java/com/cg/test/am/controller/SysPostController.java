package com.cg.test.am.controller;

import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysPostService;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.response.SysTree;
import com.cg.test.am.vo.response.TreePermissionResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "岗位")
@RestController
@RequestMapping("/sysPost")
public class SysPostController {

    @Resource
    SysPostService sysPostService;

    @ApiOperation(value = "岗位树形图", notes = "管理端API", response = TreePermissionResp.class)
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/tree")
    public ChorResponse<List<SysTree>> treeList() {

        List<SysTree> resp = sysPostService.treeList(ParamsConstant.DEL_FLAG_DEFAULT_VALUE);
        return ChorResponseUtils.success(resp);

    }

    @ApiOperation(value = "用户岗位回显树形图", notes = "管理端API", response = TreePermissionResp.class)
    @ApiResponses({
            @ApiResponse(code = 000000, message = "success"),
            @ApiResponse(code = 500001, message = "系统繁忙")
    })
    @GetMapping("/echoTree")
    public ChorResponse<List<SysTree>> echoTree(@RequestParam("userId") Long userId) {

        List<SysTree> resp = sysPostService.treeList(userId);
        return ChorResponseUtils.success(resp);

    }
}
