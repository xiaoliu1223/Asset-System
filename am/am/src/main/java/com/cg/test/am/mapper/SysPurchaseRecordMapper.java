package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysPurchaseRecord;
import com.cg.test.am.vo.request.SysPurchaseRecordListReq;
import com.cg.test.am.vo.response.SysAssetPurchaseAnalysisResp;
import com.cg.test.am.vo.response.SysPurchaseRecordListResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysPurchaseRecordMapper extends BaseMapper<SysPurchaseRecord> {
    int deleteByPrimaryKey(Long id);

    int insert(SysPurchaseRecord record);

    int insertSelective(SysPurchaseRecord record);

    SysPurchaseRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPurchaseRecord record);

    int updateByPrimaryKey(SysPurchaseRecord record);

    int count(@Param("params") SysPurchaseRecordListReq params);

    List<SysPurchaseRecordListResp> list(@Param("params") SysPurchaseRecordListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 统计采购数量
     * @param params
     * @return
     */
    SysAssetPurchaseAnalysisResp sts(@Param("params") Map<String, Object> params);
}