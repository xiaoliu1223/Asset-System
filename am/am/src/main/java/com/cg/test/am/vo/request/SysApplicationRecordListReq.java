package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysApplicationRecordListReq extends Pagination implements Serializable {

    private static final long serialVersionUID = -8001428392576687974L;

    @ApiModelProperty(value = "工单号", hidden = true)
    private String jobNo;

    @ApiModelProperty(value = "申请人姓名", hidden = true)
    private String username;

    @ApiModelProperty("资产名称")
    private String assetName;

    @ApiModelProperty(value = "资产类别", hidden = true)
    private Integer assetType;

    @ApiModelProperty("状态-->0：待审批；1：通过；-1：驳回；2：主动撤销；-2：审批中")
    private Integer status;

    @ApiModelProperty("部门")
    private String departmentId;

    @ApiModelProperty("部门[必传参:登录接口返回的departmentIds]")
    private String departmentIds;

}
