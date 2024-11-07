package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysUserUpdateReq implements Serializable {

    private static final long serialVersionUID = -747631034585623506L;

    @ApiModelProperty(value = "部门")
    private Long departmentId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String tel;

    @ApiModelProperty(value = "icon")
    private String icon;

    @ApiModelProperty(value = "角色")
    private List<Long> roleIds;

    @ApiModelProperty(value = "岗位id")
    private String postId;
}
