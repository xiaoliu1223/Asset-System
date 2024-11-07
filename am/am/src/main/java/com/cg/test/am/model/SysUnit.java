package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "sys_unit")
public class SysUnit {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "计量单位名称")
    private String name;

    @ApiModelProperty(value = "是否删除：-1：删除；0：正常")
    private Integer delFlag;
}