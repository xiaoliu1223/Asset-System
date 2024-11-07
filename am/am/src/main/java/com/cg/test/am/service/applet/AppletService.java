package com.cg.test.am.service.applet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AppletService {

    /**
     * 小程序消息推送模块验证API
     * @param request
     * @param response
     */
    void check(HttpServletRequest request, HttpServletResponse response);
}
