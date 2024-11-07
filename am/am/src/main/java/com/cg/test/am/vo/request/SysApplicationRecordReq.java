package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SysApplicationRecordReq implements Serializable {

    private static final long serialVersionUID = -4883584261606468351L;

    @ApiModelProperty("资产名称")
    private String assetName;

    @ApiModelProperty("资产类别")
    private Long assetType;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty(value = "单位：千克、页、米")
    private String units;

    @ApiModelProperty("预算单价")
    private BigDecimal budgetPrice;

    @ApiModelProperty("具体描述")
    private String description;

    @ApiModelProperty("规格/型号")
    private String specification;

    @ApiModelProperty(value = "部门id")
    private Long departmentId;

    @ApiModelProperty(value = "系统用户id")
    private Integer userId;

//    private List<SysAuditLog> sysAuditLogs;

}
