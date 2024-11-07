package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysRolePermission;

public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    int deleteByPrimaryKey(SysRolePermission key);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);
}