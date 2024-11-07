package com.cg.test.am.service;

import com.cg.test.am.vo.request.LabelPrinterReq;
import com.cg.test.am.vo.response.LabelPrinterResp;

public interface LabelPrinterService {

    /***
     * 二维码图片转base64
     * @param req
     * @param userId
     * @return
     */
    LabelPrinterResp generatorQrCode(LabelPrinterReq req, Long userId);
}
