package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysCheckLogListResp implements Serializable {

    private static final long serialVersionUID = -1869884474026537413L;

    @ApiModelProperty("盘点人id")
    private Integer checkBy;

    @ApiModelProperty("盘点人")
    private String checkName;

    @ApiModelProperty("盘点时间")
    private Long checkTime;

    @ApiModelProperty("编码")
    private String assetCode;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("资产名称")
    private String assetName;

    @ApiModelProperty("部门名称")
    private String  departmentName;

    @ApiModelProperty("资产类型")
    private String  assetTypeName;

    private Long departmentId;

    private Long assetType;
}
