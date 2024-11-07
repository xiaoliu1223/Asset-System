package com.cg.test.am.service;

import com.cg.test.am.vo.request.SysCheckLogListReq;
import com.cg.test.am.vo.request.SysCheckLogReq;
import com.cg.test.am.vo.response.SysCheckLogResp;

import java.util.List;
import java.util.Map;

public interface SysCheckLogService {

    /**
     * 日志列表
     * @param req
     */
    Map<String,Object> list(SysCheckLogListReq req);

    /**
     * 添加盘点日志
     */
    void save(SysCheckLogReq req);

    /**
     * 根据编码查询所有盘点日志
     */
    List<SysCheckLogResp> detail(String assetCode);
}
