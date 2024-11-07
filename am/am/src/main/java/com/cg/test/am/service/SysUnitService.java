package com.cg.test.am.service;

import com.cg.test.am.vo.request.SysUnitReq;
import com.cg.test.am.vo.response.SysUnitResp;

import java.util.List;

public interface SysUnitService {

    /**
     * 创建计量单位
     * @param req
     */
    void save(SysUnitReq req);

    /**
     * 计量单位列表
     * @return
     */
    List<SysUnitResp> list();

    /**
     * 删除
     * @param id
     */
    void remove(Long id);
}
