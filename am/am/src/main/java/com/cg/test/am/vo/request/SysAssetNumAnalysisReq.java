package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysAssetNumAnalysisReq implements Serializable {
    private static final long serialVersionUID = 5867401653463984341L;

    @ApiModelProperty(value = "部门id集合")
    private List<Integer> departmentIds;
}
