package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SysPurchaseRecordListResp implements Serializable {

    private static final long serialVersionUID = 95434705656489374L;

    private Long id;

    @ApiModelProperty("采购工单号")
    private String jobNo;

    @ApiModelProperty("采购人id")
    private Integer buyer;

    @ApiModelProperty("采购申请人id")
    private Integer userId;

    @ApiModelProperty("采购申请人用户名")
    private String username;

    @ApiModelProperty("资产名称")
    private String assetName;

    @ApiModelProperty("资产类别")
    private Integer assetType;

    @ApiModelProperty(value = "资产类别名称")
    private String assetTypeName;

    @ApiModelProperty("预算数量")
    private Integer budgetNum;

    @ApiModelProperty("预算价格")
    private BigDecimal budgetPrice;

    @ApiModelProperty("实际数量")
    private Integer actualNum;

    @ApiModelProperty("实际价格")
    private BigDecimal actualPrice;

    @ApiModelProperty("采购总额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "单位，m、kg")
    private String units;

    @ApiModelProperty(value = "实际单位，m、kg")
    private String actualUnits;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("采购人状态：0：采购中；1：采购完成已入库")
    private Integer purchaseStatus;

    @ApiModelProperty("采购申请日期")
    private Long createTime;

    @ApiModelProperty(value = "采购日期")
    private Long purchaseTime;

    private Long updateTime;

    @ApiModelProperty("规格型号")
    private String specification;

    @ApiModelProperty("实际规格型号")
    private String actualSpecification;

    @ApiModelProperty("采购人姓名")
    private String buyerName;

    private String purchaseStatusText;

    @ApiModelProperty(value = "提出采购申请部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "提出采购申请部门")
    private String departmentName;

    @ApiModelProperty(value = "资产编号--采购完成后自动生成")
    private String assetCode;

    public String getPurchaseStatusText(){
        switch (this.purchaseStatus){
            case 0:
                purchaseStatusText =  "采购中";
            break;
            case 1:
                purchaseStatusText =  "采购完成";
                break;
        }
        return purchaseStatusText;
    }

    @ApiModelProperty("实际采购总金额")
    private BigDecimal actualTotalMount;
}
