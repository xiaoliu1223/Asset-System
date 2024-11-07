package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysPurchaseRecordListReq extends Pagination {

    private static final long serialVersionUID = -3390695213014682123L;

    @ApiModelProperty(value = "工单号", hidden = true)
    private String jobNo;

    @ApiModelProperty(value = "申请人姓名", hidden = true)
    private String username;

    @ApiModelProperty("资产名称")
    private String assetName;

    @ApiModelProperty(value = "资产类别", hidden = true)
    private Integer assetType;

    @ApiModelProperty("采购状态--> 0：采购中；1：采购完成已入库")
    private Integer purchaseStatus;

    @ApiModelProperty(value = "采购人姓名", hidden = true)
    private String buyerName;

    @ApiModelProperty(value = "部门id", hidden = true)
    private String departmentId;

    @ApiModelProperty(value = "部门[必传参:登录接口返回的departmentIds]", hidden = true)
    private String departmentIds;
}
