package com.cg.test.am.utils;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户工具类
 */
public class UserUtils {

    @Resource
    HttpServletRequest request;
    public void getCurrentUser() {

        String token = request.getHeader("authentToken");

        // todo ...
    }
}
