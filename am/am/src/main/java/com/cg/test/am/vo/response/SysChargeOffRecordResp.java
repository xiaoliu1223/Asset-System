package com.cg.test.am.vo.response;

import com.cg.test.am.model.SysAuditLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SysChargeOffRecordResp implements Serializable {

    private static final long serialVersionUID = 5746659698293804019L;

    @ApiModelProperty("核销id")
    private Long id;

    @ApiModelProperty("工单号")
    private String jobNo;

    @ApiModelProperty("核销申请人id")
    private Integer userId;

    @ApiModelProperty("核销申请人姓名")
    private String username;

    @ApiModelProperty("部门id")
    private Integer departmentId;

    @ApiModelProperty("部门")
    private String departmentName;

    @ApiModelProperty("资产名称")
    private String assetName;

    @ApiModelProperty("资产类别")
    private Long assetType;

    @ApiModelProperty("资产类别")
    private String assetTypeName;

    @ApiModelProperty("资产编号")
    private String assetCode;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty("单位")
    private String units;

    @ApiModelProperty("核销理由")
    private String description;

    @ApiModelProperty("审批流程")
    private Integer flowPath;

    @ApiModelProperty("审批状态0:待审批；1:审批通过;-1:驳回;2:主动撤销;3:审批中;4:已核销")
    private Integer status;

    @ApiModelProperty("审批申请时间")
    private Long createTime;

    private Long updateTime;

    @ApiModelProperty("规格/型号")
    private String specification;

    @ApiModelProperty("核销金额")
    private BigDecimal pinAmount;

    @ApiModelProperty("审批流程")
    private  List<SysAuditLog>  sysAuditLogs;

    @ApiModelProperty(value = "状态文字")
    private String statusText;

    @ApiModelProperty(value = "驳回理由")
    private String reason;

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
                statusText="已核销";
                break;
            case -2:
                statusText="审核中";
                break;
        }
        return statusText;
    }
}
