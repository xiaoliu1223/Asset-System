package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.vo.request.SysDeparymentListReq;
import com.cg.test.am.model.SysDepartment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {

    int deleteByPrimaryKey(Long id);

    int insert(SysDepartment record);

    int insertSelective(SysDepartment record);

    SysDepartment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDepartment record);

    int updateByPrimaryKey(SysDepartment record);

    int count(@Param("params") SysDeparymentListReq params);

    List<SysDepartment> list(@Param("params") SysDeparymentListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("select department_id from sys_user where id = #{id}")
    String selectByIdGetDep(Integer id);

    @Select("select id  from sys_department where pid = #{id}")
    List<Long>  byPidGetId(Long pid);

    List<SysDepartment> listByUser(@Param("id") Integer id);
}