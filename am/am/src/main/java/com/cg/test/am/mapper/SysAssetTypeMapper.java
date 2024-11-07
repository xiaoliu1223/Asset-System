package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysAssetType;
import com.cg.test.am.vo.request.SysAssetTypeListReq;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysAssetTypeMapper extends BaseMapper<SysAssetType> {

    int deleteByPrimaryKey(Long id);

    int insert(SysAssetType record);

    int insertSelective(SysAssetType record);

    SysAssetType selectByPrimaryKey(Long id);

    @Select("select pid from sys_asset_type where id = #{id}")
    Long getPId(Long id);

    int updateByPrimaryKeySelective(SysAssetType record);

    int updateByPrimaryKey(SysAssetType record);

    int count(@Param("params") SysAssetTypeListReq params);

    List<SysAssetType> list(@Param("params") SysAssetTypeListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);


    /**
     * 通过最上级id，获取该类别下的资产类别集合
     * @param superId
     * @return
     */
    List<Long> idList(@Param("superId") Long superId);
}