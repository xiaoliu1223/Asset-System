package com.cg.test.am.vo.response;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class RoleHavePermissionResp implements Serializable {

    private static final long serialVersionUID = -1806358084503150843L;

    private Long id;

    private Long pid;

    private String name;

    private String path;

    private String permission;

    private String keyword;

    private List<RoleHavePermissionResp> children;

    private Integer type;
}
