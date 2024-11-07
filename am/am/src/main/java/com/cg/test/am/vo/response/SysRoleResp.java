package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleResp implements Serializable {

    private static final long serialVersionUID = 3345742795435693630L;


    private Long id;

    @ApiModelProperty("角色名")
    private String name;

    @ApiModelProperty("角色简介")
    private String description;
}
