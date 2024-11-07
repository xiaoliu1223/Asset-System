package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SysAssetStatusAnalysis implements Serializable {

    private static final long serialVersionUID = 2450497509408221688L;

    @ApiModelProperty(value = "库存状态：（1：在库；2：出库；3：报废）")
    private Integer assetStatus;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "数量占比")
    private String numPercent;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "金额占比")
    private String amountPercent;



}
