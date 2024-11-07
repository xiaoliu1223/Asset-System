package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysAuditLogReq implements Serializable {

    private static final long serialVersionUID = 8419397476033941357L;

    @ApiModelProperty(value = "审批类型：1：资产申请；3：资产领用；4：资产归还；5：资产核销", hidden = true)
    private Integer type;

    @ApiModelProperty(value = "关联表id", hidden = true)
    private Long relatedId;

    @ApiModelProperty(value = "审核人员")
    private Integer auditBy;

    @ApiModelProperty(value = "审核人姓名", hidden = true)
    private String auditName;

    @ApiModelProperty(value = "审核状态：审核状态：1：通过；-1驳回")
    private Integer auditStatus;

    @ApiModelProperty(value = "驳回原因")
    private String reason;
}
