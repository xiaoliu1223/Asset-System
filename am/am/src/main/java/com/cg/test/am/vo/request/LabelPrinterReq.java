package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LabelPrinterReq implements Serializable {

    private static final long serialVersionUID = 9172073626172088841L;

    @ApiModelProperty(value = "资产编码")
    private String assetCode;
}
