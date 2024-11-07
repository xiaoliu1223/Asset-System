package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 资产领用统计分析请求体
 */
@Data
public class SysAssetReceiveAnalysisReq implements Serializable {

    private static final long serialVersionUID = 4038441528973397926L;

    @ApiModelProperty(value = "部门id集合")
    private List<Integer> departmentIds;
//
    @ApiModelProperty(value = "0：固定 + 办公；1：固定资产；2：办公资产")
    private Integer assetType = 0;

    @ApiModelProperty(value = "起始时间：7-8，7月初，8月底这2个月数据")
    private Long startTime = 0L;

    @ApiModelProperty(value = "截止时间：yyyyMM精确到月份即可")
    private Long endTime = 0L;

}
