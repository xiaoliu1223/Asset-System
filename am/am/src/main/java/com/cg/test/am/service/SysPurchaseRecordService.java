package com.cg.test.am.service;

import com.cg.test.am.vo.request.SysPurchaseRecordListReq;
import com.cg.test.am.vo.request.SysPurchaseConfirmReq;

import java.util.Map;

public interface SysPurchaseRecordService {

    /**
     * 采购列表
     * @param req
     */
    Map<String,Object> list(SysPurchaseRecordListReq req);

    /**
     * 确认采购
     */
    void confirm(SysPurchaseConfirmReq req, Long id);
}
