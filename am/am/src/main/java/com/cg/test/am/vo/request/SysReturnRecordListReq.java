package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysReturnRecordListReq extends Pagination implements Serializable {

    private static final long serialVersionUID = 5467956409878824974L;

    @ApiModelProperty("审核状态 '':全部；0：待审批；1：审批通过；-1：审批驳回；2：主动撤销；3：已归还")
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
