package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_check_log")
public class SysCheckLog implements Serializable {

    private static final long serialVersionUID = -3846091896905710790L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer checkBy;

    private String checkName;

    private Long checkTime;

    private String assetCode;

    private String remark;
}