package com.cg.test.am.vo.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SysApplicationRecordListResp implements Serializable {

    private static final long serialVersionUID = 8670625499482568941L;

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

    @ApiModelProperty(value = "单位，m、kg")
    private String units;

    @ApiModelProperty(value = "预算单价")
    private BigDecimal budgetPrice;

    @ApiModelProperty(value = "具体描述")
    private String description;

    @ApiModelProperty(value = "审批流程，当前资产申请的审批人员")
    private Integer flowPath;

    @ApiModelProperty(value = "状态id")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "修改时间")
    private Long updateTime;

    @ApiModelProperty(value = "规格型号")
    private String specification;

    @ApiModelProperty(value = "资产申请部门id")
    private Integer departmentId;

    @ApiModelProperty(value = "资产申请部门")
    private String departmentName;

    @ApiModelProperty(value = "资产类别名称")
    private String assetTypeName;

    @ApiModelProperty(value = "资产类别id")
    private Integer assetType;

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
            case -2:
                statusText="审批中";
                break;
        }
        return statusText;
    }

    @ApiModelProperty(value = "关联job_no，批量上传字段")
    private String relateJobNo;
}
