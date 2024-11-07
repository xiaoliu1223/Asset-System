package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysMessage implements Serializable {

    private static final long serialVersionUID = 1746603426991835010L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "消息类型：1：资产申请；2：采购；3：领用；4：归还；5：核销；")
    private Integer type;

    @ApiModelProperty(value = "关联表Id：分别对应申请、领用。。")
    private Long relatedId;

    @ApiModelProperty(value = "消息接收方")
    private Long toUser;

    private String content;

    private Long createTime;

    private Long updateTime;

    @ApiModelProperty(value = "消息状态--> 0：未读；1：已读")
    private Integer status;

    @ApiModelProperty(value = "审批处理状态--> 0：未审核；-1：已完成审核")
    private Integer auditStatus;

    private Integer delFlag;

}