package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysAssetStatusAnalysisResp implements Serializable {

    private static final long serialVersionUID = 1519362785500233541L;

    @ApiModelProperty(value = "资产类别")
    private Integer assetType;

    @ApiModelProperty(value = "资产类别名称")
    private String assetTypeName;

    @ApiModelProperty(value = "资产状态分析详情")
    private List<SysAssetStatusAnalysis> analysisList;
}
