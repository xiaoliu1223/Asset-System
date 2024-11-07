package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysLockResp implements Serializable {
    private static final long serialVersionUID = -1189488394268344922L;

    @ApiModelProperty(value = "资产盘点锁是否开启-->0：关闭；1：开启")
    private Integer isLock;
}
