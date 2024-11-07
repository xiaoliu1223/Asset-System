package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class
SysChargeOffRecordReq implements Serializable {

    private static final long serialVersionUID = 534901977480126767L;

    @ApiModelProperty("库存id")
    private Long assetId;

    @ApiModelProperty("登录id")
    private Integer userId;

    @ApiModelProperty("核销数量")
    private Integer num;

//    @ApiModelProperty("核销金额")
//    private BigDecimal pinAmount;

    @ApiModelProperty("核销理由")
    private String description;


}
