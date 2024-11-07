package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysLockReq implements Serializable {

    private static final long serialVersionUID = -5529749200440902701L;

    @ApiModelProperty(value = "库存盘点锁 --> 0: 关闭；1：开启")
    private Integer isLock;
}
