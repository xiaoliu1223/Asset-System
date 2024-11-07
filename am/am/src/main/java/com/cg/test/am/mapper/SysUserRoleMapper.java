package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysUserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    int deleteByPrimaryKey(SysUserRole key);

    @Delete("delete from sys_user_role where user_id = #{userId}")
    int deleteUserRole(int userId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    int saveUserRoles(@Param("userId") Integer userId, @Param("roleIds") List<Long> roleIds);

    List<Long> getRoleIdList(@Param("userId") Integer userId);
}