package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysMessageResp implements Serializable {
    private static final long serialVersionUID = -9169045322980387357L;

    private Long id;

    @ApiModelProperty(value = "消息类型：1：资产申请；2：采购；3：领用；4：归还；5：核销；")
    private Integer type;

    @ApiModelProperty(value = "关联表Id：分别对应申请、领用。。")
    private Long relatedId;

    @ApiModelProperty(value = "消息接收方")
    private Long toUser;

    @ApiModelProperty(value = "消息具体内容")
    private String content;

    @ApiModelProperty(value = "消息接收时间")
    private Long createTime;

    private Long updateTime;

    @ApiModelProperty(value = "消息状态--> 0：未读；1：已读")
    private Integer status;

    @ApiModelProperty(value = "审批处理状态--> 0：未审核；-1：已完成审核")
    private Integer auditStatus;

}
