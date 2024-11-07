package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_department")
public class SysDepartment implements Serializable {

    private static final long serialVersionUID = -2880529013272404750L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long pid;

    private String name;

    private String description;

    private Long createTime;

    private Long createBy;

    private Long updateTime;

    private Long updateBy;

    private Integer delFlag;

    private String subIds;

    @TableField(exist = false)
    private String  pName;

}