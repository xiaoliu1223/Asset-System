package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysPermission;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}