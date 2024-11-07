package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysAssetTypeListReq extends Pagination {

    private static final long serialVersionUID = 7905871468485311791L;

    @ApiModelProperty(value = "资产类型名称")
    private String name;

    @ApiModelProperty(value = "父类id")
    private Integer pid;

}
