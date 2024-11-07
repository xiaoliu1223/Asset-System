package com.cg.test.am.service;

import com.cg.test.am.vo.request.SysLoginLogListReq;

import java.util.Map;

public interface SysLoginLogsService {

    Map<String, Object> list(SysLoginLogListReq req);

}
