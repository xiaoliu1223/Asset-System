package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class SysAssetCustomizedListReq extends Pagination implements Serializable {

    private static final long serialVersionUID = -1565115369360505793L;

    @ApiModelProperty(value = "部门id")
    private Long departmentId;

    @ApiModelProperty(value = "资产名称")
    private String assetName;

    @ApiModelProperty(value = "最低价")
    private BigDecimal lowestPrice = new BigDecimal(BigInteger.ZERO);

    @ApiModelProperty(value = "最高价")
    private BigDecimal highestPrice;

}
