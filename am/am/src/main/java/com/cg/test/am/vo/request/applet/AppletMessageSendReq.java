package com.cg.test.am.vo.request.applet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppletMessageSendReq implements Serializable {

    private static final long serialVersionUID = 16077640513673060L;

    @ApiModelProperty(value = "用户openid，可以是小程序的openid，也可以是mp_template_msg.appid对应的公众号的openid", required = true)
    private String touser;

    @ApiModelProperty(value = "小程序模板消息相关的信息，可以参考小程序模板消息接口; 有此节点则优先发送小程序模板消息")
    private WeAppTemplateMsg weapp_template_msg;

    @ApiModelProperty(value = "公众号模板消息相关的信息，可以参考公众号模板消息接口；有此节点并且没有weapp_template_msg节点时，发送公众号模板消息", required = true)
    private MpTemplateMsg mp_template_msg;

}
