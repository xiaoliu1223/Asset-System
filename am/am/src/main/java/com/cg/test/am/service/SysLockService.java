package com.cg.test.am.service;

import com.cg.test.am.vo.request.SysLockReq;
import com.cg.test.am.vo.response.SysLockResp;

public interface SysLockService {

    /**
     * 开启关闭盘点锁
     * @param req
     */
    void switchAssetLock(SysLockReq req);

    /**
     * 资产盘点开关状态
     * @return
     */
    SysLockResp getLockStatus();
}
