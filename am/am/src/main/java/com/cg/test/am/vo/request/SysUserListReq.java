package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserListReq extends Pagination {

    private static final long serialVersionUID = 8001505691334211760L;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty(value = "部门id")
    private Long departmentId;

}
