package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysAssetName;

public interface SysAssetNameMapper extends BaseMapper<SysAssetName> {
    int deleteByPrimaryKey(Long id);

    int insert(SysAssetName record);

    int insertSelective(SysAssetName record);

    SysAssetName selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysAssetName record);

    int updateByPrimaryKey(SysAssetName record);
}