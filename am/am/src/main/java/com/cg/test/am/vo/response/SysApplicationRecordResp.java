package com.cg.test.am.vo.response;

import com.cg.test.am.model.SysAuditLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SysApplicationRecordResp implements Serializable {
    private static final long serialVersionUID = 1645053828035247774L;

    private Long id;

    @ApiModelProperty("资产名称")
    private String assetName;

    @ApiModelProperty("资产类别")
    private Long assetType;

    @ApiModelProperty(value = "资产类别名称")
    private String assetTypeName;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty(value = "单位：千克、页、米")
    private String units;

    @ApiModelProperty("预算单价")
    private BigDecimal budgetPrice;

    @ApiModelProperty("具体描述")
    private String description;

    @ApiModelProperty("规格/型号")
    private String specification;

    @ApiModelProperty(value = "部门id")
    private Long departmentId;

    @ApiModelProperty(value = "资产申请部门")
    private String departmentName;

    @ApiModelProperty(value = "工单号")
    private String jobNo;

    @ApiModelProperty(value = "申请人id")
    private Integer userId;

    @ApiModelProperty(value = "申请人姓名")
    private String username;

    @ApiModelProperty(value = "审批流程，当前资产申请的审批人员")
    private Integer flowPath;

    @ApiModelProperty(value = "状态: 0：审批中；1：通过；-1：驳回；2：主动撤销；-2：审批中")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "修改时间")
    private Long updateTime;

    @ApiModelProperty(value = "审批日志")
    private List<SysAuditLog> sysAuditLogs;

    @ApiModelProperty(value = "状态文字")
    private String statusText;

    public String getStatusText() {
        switch (this.status) {
            case 0:
                statusText = "待审批";
                break;
            case 1:
                statusText = "通过";
                break;
            case -1:
                statusText = "驳回";
                break;
            case 2:
                statusText = "撤销";
                break;
            case -2:
                statusText = "审核中";
                break;
        }
        return statusText;
    }

    @ApiModelProperty(value = "关联job_no，批量上传字段")
    private String relateJobNo;

    @ApiModelProperty(value = "总价")
    private BigDecimal totalPrice;
}
