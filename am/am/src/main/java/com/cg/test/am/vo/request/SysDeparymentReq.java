package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysDeparymentReq implements Serializable {

    private static final long serialVersionUID = 8235592546203070144L;

    @ApiModelProperty(value = "上级id")
    private Long pid=0L;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

}
