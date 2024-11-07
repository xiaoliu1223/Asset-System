package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cg.test.am.vo.request.SysApplicationRecordListReq;
import com.cg.test.am.model.SysApplicationRecord;
import com.cg.test.am.vo.response.SysApplicationRecordListResp;
import com.cg.test.am.vo.response.SysApplicationRecordResp;
import com.cg.test.am.vo.request.AuditListReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysApplicationRecordMapper extends BaseMapper<SysApplicationRecord> {
    int deleteByPrimaryKey(Long id);

    int insert(SysApplicationRecord record);

    int insertSelective(SysApplicationRecord record);

    SysApplicationRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysApplicationRecord record);

    int count(@Param("params") SysApplicationRecordListReq params);

    List<SysApplicationRecordListResp> list(@Param("params") SysApplicationRecordListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    int auditCount(@Param("params") AuditListReq params);

    List<SysApplicationRecordListResp> auditList(@Param("params") AuditListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);

    SysApplicationRecordResp getById(@Param("id") Long id);

    IPage<SysApplicationRecordListResp> getPage(Page<SysApplicationRecordListResp> page, @Param("params") AuditListReq params);

    List<SysApplicationRecordResp> getByRelateJobNo(@Param("relateJobNo")String relateJobNo);
}