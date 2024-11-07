package com.cg.test.am.vo.response;

import com.cg.test.am.model.SysAuditLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysReturnRecordResp implements Serializable {

    private static final long serialVersionUID = -9174202906072067528L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("归还人")
    private String username;

    @ApiModelProperty("归还人Id")
    private Integer userId;

    @ApiModelProperty("归还部门")
    private String departmentName;

    @ApiModelProperty("归还部门Id")
    private Integer departmentId;

    @ApiModelProperty("工单号")
    private String jobNo;

    @ApiModelProperty("资产编号")
    private String assetCode;

    @ApiModelProperty("资产名")
    private String assetName;

    @ApiModelProperty("资产类别")
    private Integer assetType;

    @ApiModelProperty("资产类别")
    private String assetTypeName;

    @ApiModelProperty("规格/型号")
    private String specification;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("单位")
    private String units;

    @ApiModelProperty("使用年限")
    private String usedAge;

    @ApiModelProperty("归还申请时间")
    private Long createTime;

    @ApiModelProperty("归还理由")
    private String description;

    @ApiModelProperty("审核状态0：待审批；1：审批通过；-1：审批驳回；2：主动撤销；3：审批中； 4：确认归还")
    private Integer status;

    @ApiModelProperty("归还物品状态1：可继续使用；-1：报废")
    private Integer assetStatus;

    private Long updateTime;

    @ApiModelProperty("审核id")
    private Integer flowPath;

    @ApiModelProperty("审批流程")
    List<SysAuditLog> sysAuditLogs;

    @ApiModelProperty(value = "驳回理由")
    private String reason;

    @ApiModelProperty(value = "状态文字")
    private String statusText;

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
                statusText="已归还";
                break;
        }
        return statusText;
    }
}
