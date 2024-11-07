package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysAssetTypeResp implements Serializable {

    private static final long serialVersionUID = 4478506483705087234L;

    @ApiModelProperty("类别id")
    private Long id;

    @ApiModelProperty("父集id")
    private Long pid;

    @ApiModelProperty("类别名")
    private String name;

    @ApiModelProperty("父集类别名")
    private String pName;
}
