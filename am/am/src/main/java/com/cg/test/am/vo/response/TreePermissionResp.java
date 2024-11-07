package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TreePermissionResp implements Serializable {

    private static final long serialVersionUID = -6137636044602651673L;

    private Long id;

    private Long pid;

    private String name;

    private String path;

    private String icon;

    private Integer sort;

    private String permission;

    private List<TreePermissionResp> children;

    @ApiModelProperty("选中状态")
    private Integer select = 0;

}
