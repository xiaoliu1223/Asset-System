package com.cg.test.am.vo.request.applet;

import lombok.Data;

import java.io.Serializable;

/**
 * 公众号模板消息的数据结构体
 */
@Data
public class MpData implements Serializable {

    private static final long serialVersionUID = -1855280628578450181L;

    private MpNote first;

    private MpNote keyword1;

    private MpNote keyword2;

    private MpNote keyword3;

    private MpNote remark;

}
