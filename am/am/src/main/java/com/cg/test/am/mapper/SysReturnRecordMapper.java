package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.model.SysReturnRecord;
import com.cg.test.am.vo.request.AuditListReq;
import com.cg.test.am.vo.request.SysReturnRecordListReq;
import com.cg.test.am.vo.response.SysReturnRecordResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysReturnRecordMapper extends BaseMapper<SysReturnRecord> {

    int deleteByPrimaryKey(Long id);

    int insert(SysReturnRecord record);

    int insertSelective(SysReturnRecord record);

    SysReturnRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysReturnRecord record);

    int updateByPrimaryKey(SysReturnRecord record);

    int count(@Param("params") SysReturnRecordListReq params);

    List<SysReturnRecordResp> list(@Param("params") SysReturnRecordListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    SysReturnRecordResp getById(Long id);

    int auditCount(@Param("params") AuditListReq params);

    List<SysReturnRecordResp> auditList(@Param("params") AuditListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

}