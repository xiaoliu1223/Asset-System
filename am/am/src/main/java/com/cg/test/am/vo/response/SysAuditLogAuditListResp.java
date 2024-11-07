package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysAuditLogAuditListResp  implements Serializable {

    private static final long serialVersionUID = -8031097070649347279L;

    @ApiModelProperty(value = "审核者姓名")
    private String auditName;

    @ApiModelProperty(value = "审核状态")
    private Integer auditStatus;

    @ApiModelProperty(value = "驳回理由")
    private String reason;

    @ApiModelProperty(value = "审核时间")
    private Long auditTime;

    @ApiModelProperty(value = "此信息归属哪个部门")
    private String departmentName;

    @ApiModelProperty(value = "此信息归属哪个部门id")
    private Long departmentId;

    @ApiModelProperty(value = "此信息归属哪个资产类别")
    private String assetTypeName;

    @ApiModelProperty(value = "此信息归属哪个部门id")
    private Long assetType;

    @ApiModelProperty(value = "工单号")
    private String jobNo;

    @ApiModelProperty(value = "此信息归属用户名")
    private String username;

    @ApiModelProperty(value = "资产名称")
    private String assetName;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "单位")
    private String units;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "规格/型号")
    private String specification;

}
