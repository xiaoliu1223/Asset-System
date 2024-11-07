package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysChargeOffRecordListReq extends Pagination {

    private static final long serialVersionUID = -6204598633720106250L;

    @ApiModelProperty("审批状态 '':全部;0:待审批；1：通过；-1：驳回；2：主动撤销；3:已核销 -2:审批中")
    private Integer status;

    @ApiModelProperty("资产名")
    private String assetName;

    @ApiModelProperty("资产编号")
    private String assetCode;

    @ApiModelProperty(value = "工单号", hidden = true)
    private String jobNo;

    @ApiModelProperty(value = "部门", hidden = true)
    private String departmentId;

    @ApiModelProperty(value = "部门[必传参:登录接口返回的departmentIds]", hidden = true)
    private String departmentIds;

}
