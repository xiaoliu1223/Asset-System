package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 资产领用申请记录表
 */
@Data
@TableName(value = "sys_receive_record")
public class SysReceiveRecord implements Serializable {

    private static final long serialVersionUID = -4025824747098818426L;
    @TableId(type = IdType.AUTO)
    private Long id;

    private String jobNo;

    private Integer userId;

    private String username;

    private Integer departmentId;

    private String assetName;

    private Integer assetType;

    private String assetCode;

    private Integer num;

    private String units;

    private String description;

    private Integer flowPath;

    private Integer status;

    private Long createTime;

    private Long updateTime;

    private String specification;

    @ApiModelProperty(value = "使用人员姓名")
    private String usedBy;

}