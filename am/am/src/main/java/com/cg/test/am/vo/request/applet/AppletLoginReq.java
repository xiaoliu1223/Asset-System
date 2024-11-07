package com.cg.test.am.vo.request.applet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppletLoginReq implements Serializable {
    private static final long serialVersionUID = -4195822569232878940L;

    @ApiModelProperty(value = "用户手机号")
    private String tel;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "小程序登录API（wx.login）获取的临时凭证")
    private String jsCode;
}
