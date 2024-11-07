package com.cg.test.am.vo.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysDeparymentResp implements Serializable {

    private static final long serialVersionUID = 1501648890863801241L;

    private String pName;

    private Long id;

    private Long pid;

    private String name;

    private String description;
}
