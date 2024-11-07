package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysUnit;

public interface SysUnitMapper extends BaseMapper<SysUnit> {
    int deleteByPrimaryKey(Long id);

    int insert(SysUnit record);

    int insertSelective(SysUnit record);

    SysUnit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUnit record);

    int updateByPrimaryKey(SysUnit record);
}