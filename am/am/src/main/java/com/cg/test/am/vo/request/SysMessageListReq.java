package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysMessageListReq extends Pagination {
    private static final long serialVersionUID = -7072696443999602993L;

    @ApiModelProperty(value = "消息类型：1：资产申请；2：采购；3：领用；4：归还；5：核销；")
    private Integer type;

}
