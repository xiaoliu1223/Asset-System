package com.cg.test.am.vo.request.applet;

import lombok.Data;

import java.io.Serializable;

/**
 * 小程序模板数据
 */
@Data
public class WeAppData implements Serializable {

    private static final long serialVersionUID = 4029478923916770262L;

    private WeAppNote keyword1;

    private WeAppNote keyword2;

    private WeAppNote keyword3;

    private WeAppNote keyword4;

}
