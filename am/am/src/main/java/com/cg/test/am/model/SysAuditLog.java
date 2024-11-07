package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_audit_log")
public class SysAuditLog implements Serializable {

    private static final long serialVersionUID = 8225889722964182481L;

    @TableId(type = IdType.AUTO)
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

    @TableField(exist=false)
    @ApiModelProperty(value = "申请人/审批人")
    private String postName;
}