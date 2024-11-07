package com.cg.test.am.service;

import com.cg.test.am.model.SysUser;
import com.cg.test.am.vo.request.ChangePasswordReq;
import com.cg.test.am.vo.request.SysUserUpdateReq;
import com.cg.test.am.vo.request.applet.AppletLoginReq;
import com.cg.test.am.vo.response.LoginResp;
import com.cg.test.am.vo.request.SysUserListReq;
import com.cg.test.am.vo.request.SysUserReq;
import com.cg.test.am.vo.response.SysUserResp;

import java.util.Map;

public interface SysUserService {

    /**
     * 登录效验
     *
     * @param user
     * @return
     */
    LoginResp apiLogin(SysUserReq user);

    /**
     * 用户创建
     */
    void save(SysUser sysUser);

    /**
     * 用户列表
     */
    Map<String, Object> list(SysUserListReq req);

    /**
     * 用户详情
     */
    SysUserResp find(Integer id);

    /**
     * 用户修改
     */
    void modify(SysUserUpdateReq req, Integer id);

    /**
     * 删除用户
     */
    void remove(Integer id);

    /**
     * 密码初始化
     */
    void initial(Integer id);

    /**
     * 密码修改
     */
    void changePassword(ChangePasswordReq req);


    LoginResp appletLogin(AppletLoginReq req);
}
