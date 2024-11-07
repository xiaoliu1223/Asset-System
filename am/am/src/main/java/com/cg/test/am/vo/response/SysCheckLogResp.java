package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysCheckLogResp implements Serializable {

    private static final long serialVersionUID = -4661779394598143745L;

    @ApiModelProperty("盘点人id")
    private Integer checkBy;

    @ApiModelProperty("盘点人")
    private String checkName;

    @ApiModelProperty("盘点时间")
    private Long checkTime;

    @ApiModelProperty("编码")
    private String assetCode;

    @ApiModelProperty("备注")
    private String remark;

}
