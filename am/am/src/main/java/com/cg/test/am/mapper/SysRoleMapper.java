package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.vo.request.SysRoleListReq;
import com.cg.test.am.vo.response.GeneralResp;
import com.cg.test.am.model.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    int count(@Param("params") SysRoleListReq params);

    @Select("select id,name from sys_role where del_flag = 0")
    List<GeneralResp> selectAllList();

    @Select("select * from sys_role r inner join sys_user_role ru on r.id = ru.role_id where ru.user_id = #{userId}")
    List<GeneralResp> listByUserId(Long userId);

    @Select("select srp.permission_id as id,sp.name as name  from  sys_role_permission srp left join sys_permission sp on sp.id = srp.permission_id where srp.role_id = #{roleId}")
    List<GeneralResp> listByRoleId(Long roleId);

    List<SysRole> list(@Param("params") SysRoleListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Delete("delete from sys_role_permission where role_id = #{roleId}")
    int deleteRolePermission(Long roleId);

    int saveRolePermission(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
}