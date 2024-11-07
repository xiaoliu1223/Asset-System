package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Pagination implements Serializable {

    private static final long serialVersionUID = 2176403433303652042L;

    @ApiModelProperty(value = "页")
    private Integer current = 1;

    @ApiModelProperty(value = "每页显示行数")
    private Integer limit = 10;
}
