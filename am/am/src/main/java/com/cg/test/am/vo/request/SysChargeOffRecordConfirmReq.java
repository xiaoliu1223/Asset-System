package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SysChargeOffRecordConfirmReq implements Serializable {
    private static final long serialVersionUID = -4465028971114108324L;

    @ApiModelProperty(value = "核销金额", required = true)
    private BigDecimal scrapAmount;
}
