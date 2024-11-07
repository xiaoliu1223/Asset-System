package com.cg.test.am.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_asset_name")
public class SysAssetName {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long pid;

    private String name;

    private Long createTime;

    private Long updateTime;

    private Integer delFlag;

}