package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysDeparymentListReq extends Pagination implements Serializable {

    private static final long serialVersionUID = -129658062341754793L;

    @ApiModelProperty(value = "部门名称")
    private String name;

}
