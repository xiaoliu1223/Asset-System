package com.cg.test.am.vo.response;

import com.cg.test.am.model.SysAuditLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysReceiveRecordListResp implements Serializable {

    private static final long serialVersionUID = 1169815714466697508L;

    private Long id;

    @ApiModelProperty(value = "工单号")
    private String jobNo;

    @ApiModelProperty(value = "申请人id")
    private Integer userId;

    @ApiModelProperty(value = "申请人姓名")
    private String username;

    @ApiModelProperty(value = "资产名称")
    private String assetName;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "资产编号")
    private String assetCode;

    @ApiModelProperty(value = "具体描述")
    private String description;

    @ApiModelProperty(value = "审批流程")
    private Integer flowPath;

    @ApiModelProperty(value = "状态0：待审核；1：通过；-1：驳回；2：主动撤销；3：已领用；4：归还中；5：已归还")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "修改时间")
    private Long updateTime;

    @ApiModelProperty(value = "规格型号")
    private String specification;

    @ApiModelProperty(value = "部门Id")
    private Integer departmentId;

    @ApiModelProperty(value = "部门")
    private String departmentName;

    @ApiModelProperty(value = "资产类别Id")
    private Integer assetType;

    @ApiModelProperty(value = "资产类别")
    private String assetTypeName;

    @ApiModelProperty(value = "状态文字")
    private String statusText;

    @ApiModelProperty(value = "单位")
    private String units;

//    @ApiModelProperty(value = "驳回理由")
//    private String reason;

    @ApiModelProperty(value = "使用姓名")
    private String usedBy;

    public String getStatusText() {
        switch (this.status) {
            case 0:
                statusText="待审批";
                break;
            case 1:
                statusText="通过";
                break;
            case -1:
                statusText="驳回";
                break;
            case 2:
                statusText="撤销";
                break;
            case 3:
                statusText="已领用";
                break;
            case 4:
                statusText="归还中";
                break;
            case 5:
                statusText="已归还";
                break;
        }
        return statusText;
    }

    @ApiModelProperty("审批流程")
    List<SysAuditLog> sysAuditLogs;
}
