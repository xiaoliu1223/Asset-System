package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserReq implements Serializable {

    private static final long serialVersionUID = -5122901910940995468L;

    @ApiModelProperty(value = "登录账号：手机号或者用户名")
    private String certificate;

    @ApiModelProperty(value = "密码")
    private String password;
}
