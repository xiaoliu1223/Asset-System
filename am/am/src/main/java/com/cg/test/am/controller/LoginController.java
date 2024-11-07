package com.cg.test.am.controller;

import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.vo.request.applet.AppletLoginReq;
import com.cg.test.am.vo.response.LoginResp;
import com.cg.test.am.vo.request.SysUserReq;
import com.cg.test.am.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "登录")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    SysUserService sysuserService;

    @ApiOperation(value = "登录接口")
    @PostMapping
    public ChorResponse<LoginResp> apiLogin(@RequestBody SysUserReq user) {
        return ChorResponseUtils.success(sysuserService.apiLogin(user));
    }

    @ApiOperation(value = "小程序登录接口")
    @PostMapping("/applet")
    public ChorResponse<LoginResp> appletLogin(@RequestBody AppletLoginReq req) {

        return ChorResponseUtils.success(sysuserService.appletLogin(req));
    }
}
