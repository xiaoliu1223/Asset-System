package com.cg.test.am.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysAssetNameResp implements Serializable {
    private static final long serialVersionUID = 2696744083151143609L;

    private Long id;

    private String name;

    private Long createTime;

    private Long updateTime;
}
