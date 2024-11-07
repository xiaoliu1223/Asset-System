package com.cg.test.am.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysLock implements Serializable {

    private static final long serialVersionUID = -3150694981362624231L;
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer isLock;

    private Long updateTime;

    private Integer updateBy;
}