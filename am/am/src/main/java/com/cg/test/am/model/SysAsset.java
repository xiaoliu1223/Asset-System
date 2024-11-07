package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName(value = "sys_asset")
public class SysAsset implements Serializable {

    private static final long serialVersionUID = 1824336191040331551L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String assetCode;

    private Long assetType;

    private String assetName;

    private Integer assetNum;

    private String units;

    private BigDecimal price;

    private Integer departmentId;

    private BigDecimal chargeOffMoney;

    private Integer checkStatus;

    private Integer inventoryStatus;

    private Integer assetStatus;

    private Long createTime;

    private Integer userId;

    private String username;

    private Long updateTime;

    private Integer delFlag;

    private String specification;

    private Integer outNum;

    private Integer chargeOffNum;

    private Integer isLocked;

}