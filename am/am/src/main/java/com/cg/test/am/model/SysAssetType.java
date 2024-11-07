package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_asset_type")
public class SysAssetType implements Serializable {

    private static final long serialVersionUID = 7327693442597506148L;
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long pid;

    private String name;

    private Long createTime;

    private Long createBy;

    private Long updateTime;

    private Long updateBy;

    private Long superId;

    private Integer delFlag;

    @TableField(exist = false)
    private String pName;

}