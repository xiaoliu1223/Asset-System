package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cg.test.am.vo.request.SysAssetListReq;
import com.cg.test.am.vo.response.SysAssetNumAnalysisResp;
import com.cg.test.am.vo.response.SysAssetStatusAnalysis;
import com.cg.test.am.model.SysAsset;
import com.cg.test.am.vo.request.SysAssetCustomizedListReq;
import com.cg.test.am.vo.request.SysAssetNumAnalysisReq;
import com.cg.test.am.vo.response.SysAssetResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface SysAssetMapper extends BaseMapper<SysAsset> {
    int deleteByPrimaryKey(Long id);

    int insert(SysAsset record);

    int insertSelective(SysAsset record);

    SysAsset selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysAsset record);

    int updateByPrimaryKey(SysAsset record);

    Long count(@Param("params") SysAssetListReq params);

    List<SysAssetResp> list(@Param("params") SysAssetListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    SysAssetResp getOne(Long id);

    SysAssetStatusAnalysis sts(@Param("params") Map<String, Object> params);

    @Update("update sys_asset set inventory_status = #{status},out_num = out_num-#{num},update_time = #{time},is_locked = #{locked} where asset_code = #{assetCode}")
    int updateInventoryStatus(@Param("assetCode") String assetCode,@Param("status") Integer status,@Param("num")Integer num,@Param("time") Long time, @Param("locked")Integer locked);

    @Update("update sys_asset set inventory_status = #{inventoryStatus},out_num = out_num + #{num},update_time = #{time} where asset_code = #{assetCode}")
    int outbound(@Param("assetCode") String assetCode,@Param("inventoryStatus") Integer inventoryStatus,@Param("num") Integer num,@Param("time") Long time);

    @Update("update sys_asset set inventory_status = #{inventoryStatus},charge_off_num = #{num},asset_status = #{assetStatus},update_time = #{time} where asset_code = #{assetCode}")
    int scrap(String assetCode,Integer inventoryStatus,Integer num,Integer assetStatus,Long time);


    List<SysAssetNumAnalysisResp> assetNumAnalysisRespList(@Param("params") SysAssetNumAnalysisReq params);

    List<LinkedHashMap<String, Object>> downloadExcel(@Param("departmentIds") String departmentIds);

    IPage<SysAssetResp> selectCustomizedPage(Page<SysAssetResp> page, @Param("params") SysAssetCustomizedListReq params);
}