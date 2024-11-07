package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysAssetNameReq implements Serializable {
    private static final long serialVersionUID = -7433383937445987831L;


    @ApiModelProperty(value = "父级", hidden = true)
    private Long pid;

    @ApiModelProperty(value = "资产名称")
    private String name;
}
