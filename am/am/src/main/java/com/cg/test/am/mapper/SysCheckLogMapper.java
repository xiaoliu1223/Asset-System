package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysCheckLog;
import com.cg.test.am.vo.response.SysCheckLogListResp;
import com.cg.test.am.vo.request.SysCheckLogListReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysCheckLogMapper extends BaseMapper<SysCheckLog> {
    int deleteByPrimaryKey(Long id);

    int insert(SysCheckLog record);

    int insertSelective(SysCheckLog record);

    SysCheckLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysCheckLog record);

    int updateByPrimaryKey(SysCheckLog record);

    int count(@Param("params") SysCheckLogListReq params);

    List<SysCheckLogListResp> list(@Param("params") SysCheckLogListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

}