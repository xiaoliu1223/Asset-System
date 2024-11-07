package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysAssetListReq extends Pagination {

    private static final long serialVersionUID = 6343666510897205503L;

    @ApiModelProperty(value = "资产类型", hidden = true)
    private Integer assetType;

    @ApiModelProperty("资产名称")
    private String assetName;

    @ApiModelProperty(value = "资产编号")
    private String assetCode;

    @ApiModelProperty(value = "库存状态（1：在库；2：出库；3：报废）")
    private Integer inventoryStatus;

    @ApiModelProperty("资产所属部门")
    private Integer departmentId;

    @ApiModelProperty("部门[必传参:登录接口返回的departmentIds]")
    private String departmentIds;

}
