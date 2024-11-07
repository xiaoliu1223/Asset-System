package com.cg.test.am.service;

import com.cg.test.am.vo.request.SysRoleListReq;
import com.cg.test.am.vo.response.SysRoleResp;
import com.cg.test.am.vo.request.SysRoleReq;
import com.cg.test.am.vo.response.GeneralResp;
import com.cg.test.am.model.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleService {

    /**
     * 添加角色
     * @param sysRole
     */
    void save(SysRole sysRole);

    /**
     * 角色列表
     * @param req
     */
    Map<String, Object> list(SysRoleListReq req);

    /**
     * 角色详情
     * @param id
     * @return
     */
    SysRoleResp find(Long id);

    /**
     * 修改角色
     * @param req
     * @param id
     */
    void modify(SysRoleReq req, Long id);

    /**
     * 删除角色
     * @param id
     */
    void remove(Long id);

    /**
     * 角色列表
     */
    List<GeneralResp> allList();

    /**
     *角色权限列表
     */
    List<GeneralResp> listByRoleId(Long roleId);

    /**
     * 用户的角色
     */
    List<GeneralResp> listByUserId(Long userId);
}
