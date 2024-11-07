package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_resource")
public class SysResource implements Serializable {

    private static final long serialVersionUID = -7916733102461910461L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "图片等资源名称")
    private String name;

    private String md5;

    @ApiModelProperty(value = "访问路由")
    private String url;

    @ApiModelProperty(value = "图片类型")
    private String conType;

    private Long createTime;

}