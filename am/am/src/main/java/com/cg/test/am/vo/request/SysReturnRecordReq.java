package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysReturnRecordReq implements Serializable {

    private static final long serialVersionUID = -8811211718567510562L;

    @ApiModelProperty("领用id")
    private Long receiveId;

    @ApiModelProperty("归还人id")
    private Integer userId;

    @ApiModelProperty("归还理由")
    private String description;

    @ApiModelProperty("归还物品状态1：可继续使用；-1：报废")
    private Integer assetStatus;


}
