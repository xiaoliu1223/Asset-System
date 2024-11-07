package com.cg.test.am.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUnitResp implements Serializable {
    private static final long serialVersionUID = -531214443122677593L;

    private Long id;

    private String name;
}
