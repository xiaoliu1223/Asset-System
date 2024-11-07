package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_login_log")
public class SysLoginLog  implements Serializable {

    private static final long serialVersionUID = -8858900047637853461L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String ipAddress;

    private String macAddress;

    private Long loginTime;

}