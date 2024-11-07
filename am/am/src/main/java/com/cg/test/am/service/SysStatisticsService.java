package com.cg.test.am.service;

import com.cg.test.am.vo.request.SysAssetNumAnalysisReq;
import com.cg.test.am.vo.request.SysAssetPurchaseAnalysisReq;
import com.cg.test.am.vo.request.SysAssetReceiveAnalysisReq;
import com.cg.test.am.vo.request.SysAssetStatusAnalysisReq;
import com.cg.test.am.vo.response.*;

import java.util.List;

public interface SysStatisticsService {


    @Deprecated
    List<SysAssetStatusAnalysisResp> assetStatusAnalysisList(SysAssetStatusAnalysisReq req);

    /**
     * 资产各个部门状态数量
     * @param req
     * @return
     */
    List<SysAssetNumAnalysisResp> assetNumAnalysisList(SysAssetNumAnalysisReq req);

    /**
     * 资产采购统计
     * @param req
     * @return
     */
    List<SysAssetPurchaseAnalysisResp> assetPurchaseAnalysisList(SysAssetPurchaseAnalysisReq req);

    /**
     * 资产领用分析
     * @param req
     * @return
     */
    List<SysAssetReceiveAnalysisResp> assetReceiveAnalysisList(SysAssetReceiveAnalysisReq req);

    /**
     * 获取用户待审批数据统计
     * @return
     */
    SysPendingAuditStsResp getPendingAuditSts();

}
