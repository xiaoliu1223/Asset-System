package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LabelPrinterResp implements Serializable {
    private static final long serialVersionUID = 2984939276171784716L;

    @ApiModelProperty(value = "二维码图片转base64")
    private String imageBase64;
}
