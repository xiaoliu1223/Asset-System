package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_role_permission")
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 4361659414569400310L;

    private Long roleId;

    private Long permissionId;

}