package com.cg.test.am.vo.request.applet;

import lombok.Data;

import java.io.Serializable;

/**
 * 订阅消息模板
 */
@Data
public class SubScribeMessageReq implements Serializable {
    private static final long serialVersionUID = 6849724285085848812L;

    private String touser;

    private String template_id = "";

//    private String page;

    private String miniprogram_state = "formal";

    private String lang = "zh_CN";

    private SubscribeData data;
}
