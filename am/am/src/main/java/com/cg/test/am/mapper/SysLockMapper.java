package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysLock;

public interface SysLockMapper extends BaseMapper<SysLock> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLock record);

    int insertSelective(SysLock record);

    SysLock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLock record);

    int updateByPrimaryKey(SysLock record);
}