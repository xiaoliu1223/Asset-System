package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysCheckLogReq implements Serializable {

    private static final long serialVersionUID = -4014740511023828727L;

    @ApiModelProperty("盘点人id")
    private Integer checkBy;

    @ApiModelProperty("资产编号")
    private String assetCode;

    @ApiModelProperty("备注 资产情况")
    private String remark;

    @ApiModelProperty("资产状态 0:完好 1：有损坏可继续使用；2：报废")
    private Integer assetStatus;

}
