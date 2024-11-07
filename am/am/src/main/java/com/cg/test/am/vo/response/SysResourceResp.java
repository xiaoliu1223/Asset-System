package com.cg.test.am.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysResourceResp implements Serializable {
    private static final long serialVersionUID = -385345271261829793L;

    private Long id;

    private String url;

    private String name;
}
