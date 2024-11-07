package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SysAssetResp implements Serializable {

    private static final long serialVersionUID = -7334049265141999433L;

    private Long id;

    @ApiModelProperty("资产编号")
    private String assetCode;

    @ApiModelProperty("资产大类型:1-->固定资产；2-->办公资产；3-->低值易耗品")
    private Long assetSuperType;

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

    @ApiModelProperty("资产所属部门ID")
    private Integer departmentId;

    @ApiModelProperty("核销返还金额")
    private BigDecimal chargeOffMoney;

    @ApiModelProperty("盘点状态（0：未盘点；1：已盘点）")
    private Integer checkStatus;

    @ApiModelProperty("库存状态（1：在库；2：出库；3：报废）")
    private Integer inventoryStatus;

    @ApiModelProperty("资产状态（1：可继续使用；2：报废）")
    private Integer assetStatus;

    @ApiModelProperty("入库日期")
    private Long createTime;

    @ApiModelProperty("采购人员、录入人员 id")
    private Integer userId;

    @ApiModelProperty("采购人员、录入人员 姓名")
    private String username;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("规格/型号")
    private String specification;

    @ApiModelProperty("资产类型")
    private String assetTypeName;

    @ApiModelProperty("资产所属部门")
    private String departmentName;

    @ApiModelProperty(value = "出库数量")
    private Integer outNum;

    @ApiModelProperty(value = "核销数量")
    private Integer chargeOffNum;

    @ApiModelProperty(value = "使用年限")
    private String usedAge;


}
