package com.cg.test.am.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LoginResp {

    @ApiModelProperty("TOKEN")
    private String token;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("部门id")
    private Integer departmentId;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("角色")
    private List<GeneralResp> roleIds;

    @ApiModelProperty("权限")
    private List<RoleHavePermissionResp> permission;

    @ApiModelProperty("所属部门+下级所有部门ids")
    private String departmentIds;
}
