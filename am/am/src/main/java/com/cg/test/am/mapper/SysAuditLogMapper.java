package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.vo.request.SysAuditLogAuditListReq;
import com.cg.test.am.vo.response.SysAuditLogAuditListResp;
import com.cg.test.am.model.SysAuditLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAuditLogMapper extends BaseMapper<SysAuditLog> {
    int deleteByPrimaryKey(Long id);

    int insert(SysAuditLog record);

    int insertSelective(SysAuditLog record);

    SysAuditLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysAuditLog record);

    int updateByPrimaryKey(SysAuditLog record);

    int count(@Param("params") SysAuditLogAuditListReq params);

    List<SysAuditLogAuditListResp> list(@Param("params") SysAuditLogAuditListReq params, @Param("offset") Integer offset, @Param("limit") Integer limit);
}