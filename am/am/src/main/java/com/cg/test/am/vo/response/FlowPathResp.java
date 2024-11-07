package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 审批返回体
 */
@Data
public class FlowPathResp implements Serializable {
    private static final long serialVersionUID = 4248170049861793072L;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "职位")
    private String postName;

    @ApiModelProperty(value = "审批状态：-1：驳回；1：通过；null：暂未审批")
    private Integer auditStatus;

    @ApiModelProperty(value = "驳回原由")
    private String reason;


}
