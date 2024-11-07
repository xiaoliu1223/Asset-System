package com.cg.test.am.controller;

import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.utils.ChorResponseUtils;
import com.cg.test.am.service.SysAssetService;
import com.cg.test.am.service.SysStatisticsService;
import com.cg.test.am.vo.request.*;
import com.cg.test.am.vo.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "统计模块")
@RestController
@RequestMapping("/stat")
public class SysStatisticsController {


    @Resource
    SysStatisticsService sysStatisticsServiceImpl;

    @Resource
    SysAssetService sysAssetService;

    @Deprecated
    @GetMapping("/assetStatus-old")
    public ChorResponse<List<SysAssetStatusAnalysisResp>> assetStatusAnalysis (@ModelAttribute SysAssetStatusAnalysisReq req) {

        List<SysAssetStatusAnalysisResp> resp =  sysStatisticsServiceImpl.assetStatusAnalysisList(req);

        return ChorResponseUtils.success(resp);
    }

    @ApiOperation(value = "数据统计分析-- 资产状态分析", response = SysAssetNumAnalysisResp.class)
    @GetMapping("/assetStatus")
    public ChorResponse<List<SysAssetNumAnalysisResp>> assetNumAnalysis (@ModelAttribute SysAssetNumAnalysisReq req) {

        List<SysAssetNumAnalysisResp> resp = sysStatisticsServiceImpl.assetNumAnalysisList(req);
        return ChorResponseUtils.success(resp);
    }

    @ApiOperation(value = "数据统计分析-- 资产采购分析", response = SysAssetPurchaseAnalysisResp.class)
    @GetMapping("/purchase")
    public ChorResponse<List<SysAssetPurchaseAnalysisResp>> assetPurchaseAnalysis (@ModelAttribute SysAssetPurchaseAnalysisReq req) {

        List<SysAssetPurchaseAnalysisResp> resp = sysStatisticsServiceImpl.assetPurchaseAnalysisList(req);
        return ChorResponseUtils.success(resp);
    }

    @ApiOperation(value = "数据统计分析-- 资产领用分析", response = SysAssetReceiveAnalysisResp.class)
    @GetMapping("/receive")
    public ChorResponse<List<SysAssetReceiveAnalysisResp>> assetReceiveAnalysis (@ModelAttribute SysAssetReceiveAnalysisReq req) {

        List<SysAssetReceiveAnalysisResp> resp = sysStatisticsServiceImpl.assetReceiveAnalysisList(req);
        return ChorResponseUtils.success(resp);
    }

    @ApiOperation(value = "审批中心-- 待审批数据汇总", response = SysPendingAuditStsResp.class)
    @GetMapping("/pendingAuditSts")
    public ChorResponse<SysPendingAuditStsResp> getPendingAuditSts() {
        SysPendingAuditStsResp resp = sysStatisticsServiceImpl.getPendingAuditSts();
        return ChorResponseUtils.success(resp);
    }


    // TODO 集团资产管理员随时能够提取整个集团的资产信息。（比如集团电脑共多少台？哪个单位有多少台，5000以上的电脑有几台，3500以下的有几台？）

    @ApiOperation(value = "自定义资产库存列表")
    @GetMapping("/getCustomizedList")
    public ChorResponse<Map<String, Object>> getCustomizedList(@ModelAttribute SysAssetCustomizedListReq req) {

        return ChorResponseUtils.success(sysAssetService.getCustomizedList(req));

    }


}
