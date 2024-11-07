package com.cg.test.am.controller;

import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.LabelPrinterService;
import com.cg.test.am.vo.request.LabelPrinterReq;
import com.cg.test.am.vo.response.LabelPrinterResp;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "标签条码相关API")
@RestController
@RequestMapping("/printer")
public class LabelPrinterController {

    @Resource
    LabelPrinterService labelPrinterServiceImpl;

    @PostMapping("{userId}")
    public ChorResponse<LabelPrinterResp> generatorQrCode(@RequestBody LabelPrinterReq req, @PathVariable Long userId) {

        LabelPrinterResp resp = labelPrinterServiceImpl.generatorQrCode(req, userId);
        return ChorResponseUtils.success(resp);
    }
}
