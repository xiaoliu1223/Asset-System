package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysAuditLogListReq extends Pagination {

    private static final long serialVersionUID = 1728383625398777981L;

    @ApiModelProperty(value = "关联表Id", required = true)
    private Long relatedId;

    @ApiModelProperty(value = "审批类型：1：资产申请；3：资产领用；4：资产归还；5：资产核销", required = true)
    private Integer type;
}
