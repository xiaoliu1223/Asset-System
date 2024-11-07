package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysRoleReq implements Serializable {

    private static final long serialVersionUID = 3342083407830904391L;

    @ApiModelProperty("角色名")
    private String name;

    @ApiModelProperty("角色简介")
    private String description;

    @ApiModelProperty("路由许可")
    private List<Long> permissionIds;


}
