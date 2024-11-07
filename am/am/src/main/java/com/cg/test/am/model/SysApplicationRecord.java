package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 资产申请表
 */
@Data
@TableName(value = "sys_application_record")
public class SysApplicationRecord implements Serializable {

    private static final long serialVersionUID = 4422649330701966782L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String jobNo;

    private Integer userId;

    private String username;

    private Long departmentId;

    private String assetName;

    private Long assetType;

    private Integer num;

    private String units;

    private BigDecimal budgetPrice;

    private String description;

    private Integer flowPath;

    private Integer status;

    private Long createTime;

    private Long updateTime;

    private String specification;

    @TableField(exist=false)
    private String assetTypeTemp;

    private String relateJobNo;
}