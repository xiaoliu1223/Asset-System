package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_post")
public class SysPost implements Serializable {

    private static final long serialVersionUID = -617534057981184298L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    private String name;

    private Long createTime;

    private Integer createBy;

    private Integer delFlag;

}