package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SysPurchaseConfirmReq implements Serializable {

    private static final long serialVersionUID = 702044717108490845L;

    @ApiModelProperty("实际资产名称")
    private String actualAssetName;

    @ApiModelProperty("实际资产类别")
    private Long actualAssetType;

    @ApiModelProperty("实际规格型号")
    private String actualSpecification;

    @ApiModelProperty("实际数量")
    private Integer actualNum;

    @ApiModelProperty(value = "实际单位，m、kg")
    private String actualUnits;

    @ApiModelProperty("实际单价")
    private BigDecimal actualPrice;

    @ApiModelProperty(value = "采购完成日期")
    private Long purchaseTime;

    @ApiModelProperty("实际采购总金额")
    private BigDecimal actualTotalMount;

    @ApiModelProperty("采购描述")
    private String buyDescription;

    @ApiModelProperty("确认采购入库用户id")
    private Integer confirmUserId;
}
