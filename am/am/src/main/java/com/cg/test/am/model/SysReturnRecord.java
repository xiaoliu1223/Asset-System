package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_return_record")
public class SysReturnRecord implements Serializable {
    private static final long serialVersionUID = 9072188458410834070L;

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

    private String usedAge;

    private Integer assetStatus;

    private Integer flowPath;

    private Integer status;

    private Long createTime;

    private Long updateTime;

    private String specification;
}