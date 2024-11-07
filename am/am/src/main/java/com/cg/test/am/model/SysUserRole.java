package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_user_role")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 596054472967836177L;

    private Long userId;

    private Long roleId;

}