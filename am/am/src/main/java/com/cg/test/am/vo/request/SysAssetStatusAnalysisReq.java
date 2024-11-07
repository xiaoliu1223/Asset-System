package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysAssetStatusAnalysisReq implements Serializable {

    private static final long serialVersionUID = 3859669006175585360L;

    @ApiModelProperty(value = "资产状态（1：在库；2：出库；3：报废）")
    private Integer inventoryStatus;

    @ApiModelProperty(value = "部门id", hidden = true)
    private Long departmentId;
}
