package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysAssetPurchaseAnalysisReq implements Serializable {
    private static final long serialVersionUID = 5587401630155474992L;

    @ApiModelProperty(value = "起始时间：7-8，7月初，8月底这2个月数据")
    private Long startTime;

    @ApiModelProperty(value = "截止时间：yyyyMM精确到月份即可")
    private Long endTime;
}
