package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysUser;
import com.cg.test.am.vo.response.SysUserResp;
import com.cg.test.am.vo.request.SysUserListReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int count(@Param("params") SysUserListReq params);

    List<SysUserResp> list(@Param("params") SysUserListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);


}