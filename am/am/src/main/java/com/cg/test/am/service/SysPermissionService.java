package com.cg.test.am.service;

import com.cg.test.am.model.SysPermission;
import com.cg.test.am.vo.request.SysPermissionReq;
import com.cg.test.am.vo.response.RoleHavePermissionResp;
import com.cg.test.am.vo.response.SysPermissionResp;
import com.cg.test.am.vo.response.TreePermissionResp;

import java.util.List;

public interface SysPermissionService {

    List<TreePermissionResp> treeList(Long roleId);

    void save(SysPermission sysPermission);

    void remove(Long id);

    void modify(SysPermissionReq req, Long id);

    SysPermissionResp find(Long id);

    /**
     * 根据角色ids获取拥有权限
     */
    List<RoleHavePermissionResp> getTreeListByRoleId(List<Long> roleIds);
}
