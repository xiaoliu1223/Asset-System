package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysLoginLog;
import com.cg.test.am.vo.request.SysLoginLogListReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    int deleteByPrimaryKey(Long id);

    int insert(SysLoginLog record);

    int insertSelective(SysLoginLog record);

    SysLoginLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLoginLog record);

    int updateByPrimaryKey(SysLoginLog record);

    int count(@Param("params") SysLoginLogListReq params);

    List<SysLoginLog> list(@Param("params") SysLoginLogListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);
}