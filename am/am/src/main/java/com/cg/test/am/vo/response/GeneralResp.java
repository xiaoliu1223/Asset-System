package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GeneralResp implements Serializable {

    private static final long serialVersionUID = -2391782487144618564L;

    private Long id;

    @ApiModelProperty("名称")
    private String name;
}
