package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysAuditLogAuditListReq extends Pagination implements Serializable {

    private static final long serialVersionUID = 1206428274997543513L;

    @ApiModelProperty(value = "审批类型：1：资产申请；3：资产领用；4：资产归还；5：资产核销", required = true)
    private Integer type;

    @ApiModelProperty(value = "部门[部门和登录用户id二选一传参:登录接口返回的departmentIds]")
    private String departmentIds;

    @ApiModelProperty(value = "用户id[部门和登录用户id二选一传参:登录接口返回的id]")
    private String userId;

    @ApiModelProperty(value = "资产名")
    private String assetName;

    @ApiModelProperty(value = "工单号")
    private String jobNo;
}
