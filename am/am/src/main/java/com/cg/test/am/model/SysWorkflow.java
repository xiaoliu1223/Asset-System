package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_workflow")
public class SysWorkflow implements Serializable {

    private static final long serialVersionUID = -1697934302185672513L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "部门id")
    private Long departmentId;

    @ApiModelProperty(value = "资产类别")
    private Long assetType;

    @ApiModelProperty(value = "审批类型：1：资产申请；2：资产采购；3：资产领用；4：资产归还；5：资产核销")
    private Integer auditType;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "上级用户id")
    private Integer superUserId;

    @ApiModelProperty(value = "岗位id")
    private Integer postId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "更新时间")
    private Long updateTime;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "是否删除：-1：删除；0：正常")
    private Integer delFlag;

}