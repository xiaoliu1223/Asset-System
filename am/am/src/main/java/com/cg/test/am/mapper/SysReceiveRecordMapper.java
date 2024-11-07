package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysReceiveRecord;
import com.cg.test.am.vo.request.AuditListReq;
import com.cg.test.am.vo.response.SysReceiveRecordListResp;
import com.cg.test.am.vo.request.SysReceiveRecordListReq;
import com.cg.test.am.vo.response.SysAssetReceiveAnalysis;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysReceiveRecordMapper extends BaseMapper<SysReceiveRecord> {
    int deleteByPrimaryKey(Long id);

    int insert(SysReceiveRecord record);

    int insertSelective(SysReceiveRecord record);

    SysReceiveRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysReceiveRecord record);

    int updateByPrimaryKey(SysReceiveRecord record);

    int count(@Param("params") SysReceiveRecordListReq params);

    List<SysReceiveRecordListResp> list(@Param("params") SysReceiveRecordListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    SysReceiveRecordListResp getOne(Long id);

    int auditCount(@Param("params") AuditListReq params);

    List<SysReceiveRecordListResp> auditlist(@Param("params") AuditListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 领用统计分析
     * @param params
     * @return
     */
    List<SysAssetReceiveAnalysis> sts(@Param("params") Map<String, Object> params);
}