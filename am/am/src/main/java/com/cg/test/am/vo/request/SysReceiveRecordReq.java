package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysReceiveRecordReq implements Serializable {

    private static final long serialVersionUID = 2142173182780299503L;

    @ApiModelProperty(value = "系统用户id-->领用人id")
    private Integer userId;

    @ApiModelProperty(value = "资产编号")
    private String assetCode;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "具体描述")
    private String description;

    @ApiModelProperty(value = "使用人姓名")
    private String usedBy;

}
