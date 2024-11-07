package com.cg.test.am.service;

import com.cg.test.am.vo.request.SysAssetNameReq;
import com.cg.test.am.vo.response.SysAssetNameResp;

import java.util.List;

public interface SysAssetNameService {
    void save(SysAssetNameReq req);

    List<SysAssetNameResp> list();

    void remove(Long id);
}
