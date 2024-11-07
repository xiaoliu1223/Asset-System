package com.cg.test.am.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysUserAddReq implements Serializable {

    private static final long serialVersionUID = 6513076183985043453L;

    @ApiModelProperty(value = "部门")
    private Long departmentId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "角色")
    private List<Long> roleIds;

    @ApiModelProperty(value = "电话号码")
    private String tel;

    @ApiModelProperty(value = "岗位id")
    private String postId;
}
