package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 审批中心统计 for applet
 */
@Data
public class SysPendingAuditStsResp implements Serializable {
    private static final long serialVersionUID = 5872748086789493656L;

    @ApiModelProperty(value = "合计未审批")
    private Integer totalNum;

    @ApiModelProperty(value = "资产申请待审批数量")
    private Integer applicationNum;

    @ApiModelProperty(value = "资产领用待审批数量")
    private Integer receiveNum;

    @ApiModelProperty(value = "资产归还待审批数量")
    private Integer returnNum;

    @ApiModelProperty(value = "资产核销待审批数量")
    private Integer scrapNum;
}
