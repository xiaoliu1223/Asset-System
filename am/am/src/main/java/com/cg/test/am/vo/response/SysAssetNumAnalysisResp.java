package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysAssetNumAnalysisResp implements Serializable {

    private static final long serialVersionUID = 2247512388058431642L;

    @ApiModelProperty(value = "部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ApiModelProperty(value = "在库数量")
    private Integer inNum;

    @ApiModelProperty(value = "在库占比")
    private String percentInNum;

    @ApiModelProperty(value = "出库数量")
    private Integer outNum;

    @ApiModelProperty(value = "出库占比")
    private String percentOutNum;

    @ApiModelProperty(value = "报废数量")
    private Integer scrapNum;

    @ApiModelProperty(value = "报废占比")
    private String percentScrapNum;
}
