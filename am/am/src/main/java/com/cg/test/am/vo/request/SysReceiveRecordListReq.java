package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysReceiveRecordListReq extends Pagination implements Serializable {

    private static final long serialVersionUID = 6094297350502176567L;

    @ApiModelProperty(value = "工单号", hidden = true)
    private String jobNo;

    @ApiModelProperty(value = "申请人姓名", hidden = true)
    private String username;

    @ApiModelProperty("资产名称")
    private String assetName;

    @ApiModelProperty("资产编号")
    private String assetCode;

    @ApiModelProperty(value = "资产类别", hidden = true)
    private Integer assetType;

    @ApiModelProperty("状态：0：待审批；1：通过；-1：驳回；2：主动撤销；3：已领用；4：归还中；5：已归还")
    private Integer status;

    @ApiModelProperty(value = "部门", hidden = true)
    private String departmentId;

    @ApiModelProperty(value = "部门[必传参:登录接口返回的departmentIds]", hidden = true)
    private String departmentIds;

}
