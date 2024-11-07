package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName(value = "sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = -1334674745788014205L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Long departmentId;

    private String nickname;

    private String username;

    private String tel;

    private String postId;

    private String superiorPostId;

    private String icon;

    private String password;

    private String salt;

    private Long createTime;

    private String createBy;

    private Long updateTime;

    private String updateBy;

    private Integer delFlag;

    private String openid;

    @TableField(exist = false)
    private List<Long> roleIds;

    @TableField(exist = false)
    private String departName;
}