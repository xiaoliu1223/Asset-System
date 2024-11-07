package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysChargeOffRecord;
import com.cg.test.am.vo.request.SysChargeOffRecordListReq;
import com.cg.test.am.vo.response.SysChargeOffRecordResp;
import com.cg.test.am.vo.request.AuditListReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysChargeOffRecordMapper extends BaseMapper<SysChargeOffRecord> {
    int deleteByPrimaryKey(Long id);

    int insert(SysChargeOffRecord record);

    int insertSelective(SysChargeOffRecord record);

    SysChargeOffRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysChargeOffRecord record);

    int updateByPrimaryKey(SysChargeOffRecord record);

    int count(@Param("params") SysChargeOffRecordListReq params);

    List<SysChargeOffRecordResp> list(@Param("params") SysChargeOffRecordListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    SysChargeOffRecordResp getById(Long id);

    int auditCount(@Param("params") AuditListReq params);

    List<SysChargeOffRecordResp> auditList(@Param("params") AuditListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

}