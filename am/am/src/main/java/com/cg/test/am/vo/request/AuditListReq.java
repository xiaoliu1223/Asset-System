package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuditListReq extends Pagination {

    private static final long serialVersionUID = 5993064983348751235L;

    @ApiModelProperty(value = "用户id", required = true)
    private Integer userId = 0;

    @ApiModelProperty(value = "审批状态：0：待审批；1：已审批", required = true)
    private Integer status = 0;

    @ApiModelProperty(value = "审核结果：1：通过；-1：驳回；0：查全部")
    private Integer auditResult;

    @ApiModelProperty(value = "批量申请显示一条：1是")
    private Integer batchSingle;

}
