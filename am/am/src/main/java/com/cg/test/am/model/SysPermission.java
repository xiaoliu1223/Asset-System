package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_permission")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = -1771727132305873097L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long pid;

    private String name;

    private String path;

    private String icon;

    private Integer sort;

    private String permission;

    private Integer type;

    private Integer status;

    private String keyword;

    private Long createTime;

    private Long createBy;

    private Integer delFlag;

}