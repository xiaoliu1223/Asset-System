package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysPermissionReq implements Serializable {

    private static final long serialVersionUID = 7143094670730926163L;

    private Long pid;

    private String name;

    private String path;

    private String icon;

    private Integer sort;

    @ApiModelProperty(value = "1->菜单；2->按钮")
    private Integer type;

    private String permission;

}
