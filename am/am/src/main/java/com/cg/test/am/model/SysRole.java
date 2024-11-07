package com.cg.test.am.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName(value = "sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 2927667112610582291L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Long createTime;

    private String createBy;

    private Long updateTime;

    private String updateBy;

    private Integer delFlag;

    @TableField(exist = false)
    private List<Long> permissionIds;
}