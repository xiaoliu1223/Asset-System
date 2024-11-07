package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysAuditLogResp implements Serializable {

    private static final long serialVersionUID = -2755655704807760424L;

    private Long id;

    @ApiModelProperty(value = "审批类型：1：资产申请；3：资产领用；4：资产归还；5：资产核销")
    private Integer type;

    @ApiModelProperty(value = "关联表id")
    private Long relatedId;

    @ApiModelProperty(value = "审核人员")
    private Integer auditBy;

    @ApiModelProperty(value = "审核人姓名")
    private String auditName;

    @ApiModelProperty(value = "审核状态：审核状态：1：通过；-1驳回")
    private Integer auditStatus;

    @ApiModelProperty(value = "驳回原因")
    private String reason;

    @ApiModelProperty(value = "审核时间")
    private Long auditTime;

}
