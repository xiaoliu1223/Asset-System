package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SysAssetReq implements Serializable {

    private static final long serialVersionUID = -7769047870479848591L;

    @ApiModelProperty("资产类型")
    private Long assetType;

    @ApiModelProperty("资产名称")
    private String assetName;

    @ApiModelProperty("数量")
    private Integer assetNum;

    @ApiModelProperty("单位")
    private String units;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("规格/型号")
    private String specification;

    @ApiModelProperty("资产所属部门")
    private Integer departmentId;

    @ApiModelProperty(value = "系统用户id")
    private Integer userId;

}
