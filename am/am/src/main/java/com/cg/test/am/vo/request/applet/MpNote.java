package com.cg.test.am.vo.request.applet;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信公众号消息体内部结构
 */
@Data
public class MpNote implements Serializable {

    private static final long serialVersionUID = -842746167150877266L;

    private String value;

    private String color;

    public MpNote() {
        super();
    }

    public MpNote(String value, String color) {
        super();
        this.value = value;
        this.color = color;
    }
}
