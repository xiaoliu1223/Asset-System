package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@TableName(value = "sys_charge_off_record")
public class SysChargeOffRecord implements Serializable {

    private static final long serialVersionUID = 3028168352784468174L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String jobNo;

    private Integer userId;

    private String username;

    private Integer departmentId;

    private String assetName;

    private Long assetType;

    private String assetCode;

    private Integer num;

    private String units;

    private String description;

    private Integer flowPath;

    private Integer status;

    private Long createTime;

    private Long updateTime;

    private String specification;

    @ApiModelProperty("核销金额")
    private BigDecimal pinAmount;
}