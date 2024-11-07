package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysAuditLogBatchReq implements Serializable {

    private static final long serialVersionUID = 4044893345849432150L;

    @ApiModelProperty(value = "审批状态：1：通过；-1驳回")
    private Integer auditStatus;

    @ApiModelProperty(value = "申请审批列表id集合")
    private List<Long> idList;
}
