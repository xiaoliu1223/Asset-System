package com.cg.test.am.vo.request.applet;

import lombok.Data;

import java.io.Serializable;

/**
 * 小程序数据内部结构
 */
@Data
public class WeAppNote implements Serializable {

    private static final long serialVersionUID = 7831123060773124259L;

    private String value;
}
