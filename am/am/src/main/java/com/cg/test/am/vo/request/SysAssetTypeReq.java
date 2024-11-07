package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysAssetTypeReq implements Serializable {

    private static final long serialVersionUID = 4792466486657937207L;

    @ApiModelProperty(value = "上级id")
    private Long pid = 0L;

    @ApiModelProperty(value = "类别名称")
    private String name;

}
