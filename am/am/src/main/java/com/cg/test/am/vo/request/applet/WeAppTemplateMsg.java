package com.cg.test.am.vo.request.applet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WeAppTemplateMsg implements Serializable {
    private static final long serialVersionUID = -6324796545549001585L;

    @ApiModelProperty(value = "小程序模板ID")
    private String template_id;

    @ApiModelProperty(value = "小程序页面路径")
    private String page;

    @ApiModelProperty(value = "小程序模板消息formid")
    private String form_id;

    @ApiModelProperty(value = "小程序模板数据")
    private WeAppData data;

    @ApiModelProperty(value = "小程序模板放大关键词")
    private String emphasis_keyword;


}
