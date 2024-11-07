package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysMessage;

public interface SysMessageMapper extends BaseMapper<SysMessage> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SysMessage record);

    SysMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMessage record);

    int updateByPrimaryKey(SysMessage record);
}