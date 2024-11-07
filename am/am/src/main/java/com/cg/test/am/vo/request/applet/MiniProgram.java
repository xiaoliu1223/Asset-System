package com.cg.test.am.vo.request.applet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MiniProgram implements Serializable {

    private static final long serialVersionUID = -6450754867032703357L;

    @ApiModelProperty(value = "小程序appid")
    private String appid;

    @ApiModelProperty(value = "页面路径")
    private String pagepath;
}
