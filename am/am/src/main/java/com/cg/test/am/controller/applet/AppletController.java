package com.cg.test.am.controller.applet;

import com.cg.test.am.service.applet.AppletService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "小程序消息推送配置模块")
@RestController
@RequestMapping("/applet")
public class AppletController {


    @Resource
    AppletService appletServiceImpl;

    @GetMapping("/check")
    public void check(HttpServletRequest request, HttpServletResponse response) {
        appletServiceImpl.check(request, response);
    }
}
