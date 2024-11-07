package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SysAssetReceiveAnalysis implements Serializable {
    private static final long serialVersionUID = -4145509563593702509L;

    @ApiModelProperty(value = "部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "领取数量")
    private Integer receiveNum;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

//    @ApiModelProperty(value = "数量占比")
//    private String percentNum;
}
