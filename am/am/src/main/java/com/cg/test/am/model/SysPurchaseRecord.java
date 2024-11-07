package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购记录
 */
@Data
@TableName(value = "sys_purchase_record")
public class SysPurchaseRecord implements Serializable {

    private static final long serialVersionUID = 3751475911745699043L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String jobNo;

    private Integer buyer;

    private Integer userId;

    private String username;

    private Integer departmentId;

    private String assetName;

    private String actualAssetName;

    private Long assetType;

    private Long actualAssetType;

    private Integer budgetNum;

    private BigDecimal budgetPrice;

    private Integer actualNum;

    private BigDecimal actualPrice;

    private String units;

    private String actualUnits;

    private String description;

    private Integer purchaseStatus;

    private Long createTime;

    private Long purchaseTime;

    private Long updateTime;

    private String specification;

    @ApiModelProperty("采购人姓名")
    private String buyerName;

    @ApiModelProperty("资产编号")
    private String assetCode;

    @ApiModelProperty("实际采购总金额")
    private BigDecimal actualTotalMount;

    @ApiModelProperty("采购描述")
    private String buyDescription;

}