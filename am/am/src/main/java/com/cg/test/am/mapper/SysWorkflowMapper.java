package com.cg.test.am.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cg.test.am.vo.response.FlowPathResp;
import com.cg.test.am.model.SysWorkflow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysWorkflowMapper extends BaseMapper<SysWorkflow> {
    int deleteByPrimaryKey(Long id);

    int insert(SysWorkflow record);

    int insertSelective(SysWorkflow record);

    SysWorkflow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysWorkflow record);

    int updateByPrimaryKey(SysWorkflow record);

    /**
     * 获取审批流详情
     * @param params
     * @return
     */
    List<FlowPathResp> getWorkflowList(@Param("params") Map<String, Object> params);
}