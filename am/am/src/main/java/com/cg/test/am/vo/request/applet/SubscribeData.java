package com.cg.test.am.vo.request.applet;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubscribeData implements Serializable {
    private static final long serialVersionUID = 4772965319544298309L;

    private SubscribeNote thing1;

    private SubscribeNote thing2;

    private SubscribeNote thing3;

    private SubscribeNote thing4;
}
