package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 部门、资产类型树状返回体
 */
@Data
public class SysTree implements Serializable {

    private static final long serialVersionUID = 3334103730683243954L;

    private Long id;

    private String name;

    private List<SysTree> list;

    private Long pid;

    private String description;

    @ApiModelProperty("选中状态0:未选中1:选中")
    private Integer select=0;
}
