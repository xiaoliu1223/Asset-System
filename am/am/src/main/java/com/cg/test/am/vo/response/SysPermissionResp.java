package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysPermissionResp implements Serializable {

    private static final long serialVersionUID = -1751712691265014758L;

    private Long id;

    private Long pid;

    @ApiModelProperty(value = "上级权限名称")
    private String parentName;

    private String name;

    private String path;

    private String icon;

    private Integer sort;

    private String permission;

    private String keyword;

    private Integer status;

    private Long createTime;

    private Long createBy;

}
