package com.cg.test.am.vo.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysUserResp implements Serializable {

    private static final long serialVersionUID = -4336889539503087416L;

    private Integer id;

    @ApiModelProperty(value = "部门")
    private Long departmentId;

    @ApiModelProperty(value = "部门名称")
    private String departName;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String tel;

    @ApiModelProperty(value = "头像")
    private String icon;

    private Long createTime;

    @ApiModelProperty(value = "岗位id")
    private String postId;

    @ApiModelProperty(value = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "角色id集合")
    private List<Long> roleIds;

}
