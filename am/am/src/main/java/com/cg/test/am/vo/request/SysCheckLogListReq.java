package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysCheckLogListReq  extends  Pagination  implements Serializable {

    private static final long serialVersionUID = -7395198256107694536L;

    @ApiModelProperty("资产名称")
    private String assetName;

    @ApiModelProperty("资产编号")
    private String assetCode;

}
