package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysAssetReceiveAnalysisResp implements Serializable {

    private static final long serialVersionUID = 4013814465009799532L;

    @ApiModelProperty(value = "资产类别")
    private Integer assetType;

    @ApiModelProperty(value = "资产类别名称")
    private String assetTypeName;

//    private BigDecimal totalAmount;
//
//    private Integer totalNum;

    private List<SysAssetReceiveAnalysis> sysAssetReceiveAnalysisList;


}
