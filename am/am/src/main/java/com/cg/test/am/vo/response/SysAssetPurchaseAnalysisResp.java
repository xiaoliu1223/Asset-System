package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 资产采购统计分析返回体
 */
@Data
public class SysAssetPurchaseAnalysisResp implements Serializable {

    private static final long serialVersionUID = 9061921061621535752L;

    private Integer assetType;

    @ApiModelProperty(value = "类别名称")
    private String assetTypeName;

    @ApiModelProperty(value = "采购数量")
    private Integer purchaseNum;

    @ApiModelProperty(value = "采购金额")
    private BigDecimal amount;

//    @ApiModelProperty(value = "采购数量占比")
//    private String percentNum;
}
