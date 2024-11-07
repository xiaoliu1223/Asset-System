package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.*;
import com.cg.test.am.model.SysApplicationRecord;
import com.cg.test.am.model.SysChargeOffRecord;
import com.cg.test.am.model.SysReceiveRecord;
import com.cg.test.am.model.SysReturnRecord;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysStatisticsService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.SysAssetNumAnalysisReq;
import com.cg.test.am.vo.request.SysAssetPurchaseAnalysisReq;
import com.cg.test.am.vo.request.SysAssetReceiveAnalysisReq;
import com.cg.test.am.vo.request.SysAssetStatusAnalysisReq;
import com.cg.test.am.vo.response.*;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysStatisticsServiceImpl implements SysStatisticsService {

    @Resource
    SysAssetMapper sysAssetMapper;

    @Resource
    SysAssetTypeMapper sysAssetTypeMapper;

    @Resource
    SysPurchaseRecordMapper sysPurchaseRecordMapper;

    @Resource
    SysReceiveRecordMapper sysReceiveRecordMapper;

    @Resource
    SysApplicationRecordMapper sysApplicationRecordMapper;

    @Resource
    SysReturnRecordMapper sysReturnRecordMapper;

    @Resource
    SysChargeOffRecordMapper sysChargeOffRecordMapper;

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    HttpServletRequest httpServletRequest;

    @Override
    public List<SysAssetStatusAnalysisResp> assetStatusAnalysisList(SysAssetStatusAnalysisReq req) {


        Map<String, Object> map = new HashMap<>();
        map.put("inventoryStatus", req.getInventoryStatus());

        // 所有
        SysAssetStatusAnalysis assetStatusAnalysisAll =  sysAssetMapper.sts(map);
        BigDecimal totalAmount = assetStatusAnalysisAll.getAmount();
        Integer sum = assetStatusAnalysisAll.getNum();

        // 低值易耗品
        List<Long> ids = sysAssetTypeMapper.idList(ParamsConstant.ASSET_TYPE_CONSUMABLES);
        map.put("ids", ids);

        // 最终返回结果集
        List<SysAssetStatusAnalysisResp> resps = new ArrayList<>();

        // 低值易耗品
        SysAssetStatusAnalysisResp assetStatusAnalysisConsumablesResp = new SysAssetStatusAnalysisResp();
        List<SysAssetStatusAnalysis> assetStatusAnalysesConsumables = null;
        SysAssetStatusAnalysis assetStatusAnalysis =  sysAssetMapper.sts(map);
        if (null == assetStatusAnalysis) {
            assetStatusAnalysesConsumables = nullReload(req);
        } else {
            assetStatusAnalysesConsumables = notNullReload(assetStatusAnalysis, sum, req);
        }

        assetStatusAnalysisConsumablesResp.setAssetType(Integer.parseInt(ParamsConstant.ASSET_TYPE_CONSUMABLES.toString()));
        assetStatusAnalysisConsumablesResp.setAnalysisList(assetStatusAnalysesConsumables);
        resps.add(assetStatusAnalysisConsumablesResp);

        // 办公资产
        ids = sysAssetTypeMapper.idList(ParamsConstant.ASSET_TYPE_OFFICE);
        map.put("ids", ids);

        SysAssetStatusAnalysisResp assetStatusAnalysisOfficeResp = new SysAssetStatusAnalysisResp();
        List<SysAssetStatusAnalysis> assetStatusAnalysesOffice = null;
        assetStatusAnalysis =  sysAssetMapper.sts(map);

        if (null == assetStatusAnalysis) {
            assetStatusAnalysesOffice = nullReload(req);
        } else {
            assetStatusAnalysesOffice = notNullReload(assetStatusAnalysis, sum, req);
        }

        assetStatusAnalysisOfficeResp.setAssetType(Integer.parseInt(ParamsConstant.ASSET_TYPE_OFFICE.toString()));
        assetStatusAnalysisOfficeResp.setAnalysisList(assetStatusAnalysesOffice);
        resps.add(assetStatusAnalysisOfficeResp);

        // 固定资产
        ids = sysAssetTypeMapper.idList(ParamsConstant.ASSET_TYPE_FIXED);
        map.put("ids", ids);

        SysAssetStatusAnalysisResp assetStatusAnalysisFixedResp = new SysAssetStatusAnalysisResp();
        List<SysAssetStatusAnalysis> assetStatusAnalysesFixed = null;
        assetStatusAnalysis =  sysAssetMapper.sts(map);
        if (null == assetStatusAnalysis) {
            assetStatusAnalysesFixed = nullReload(req);
        } else {
            assetStatusAnalysesFixed = notNullReload(assetStatusAnalysis, sum, req);
        }
        assetStatusAnalysisFixedResp.setAssetType(Integer.parseInt(ParamsConstant.ASSET_TYPE_FIXED.toString()));
        assetStatusAnalysisFixedResp.setAnalysisList(assetStatusAnalysesFixed);
        resps.add(assetStatusAnalysisFixedResp);
        return resps;
    }

    @Override
    public List<SysAssetNumAnalysisResp> assetNumAnalysisList(SysAssetNumAnalysisReq req) {

        try {

            List<SysAssetNumAnalysisResp> respList = sysAssetMapper.assetNumAnalysisRespList(req);

            // 数据处理
            NumberFormat numberInstance = NumberFormat.getNumberInstance();
            numberInstance.setMaximumFractionDigits(1); // 小数点后一位

            double sumInNum = 0.0;
            double sumOutNum = 0.0;
            double sumScrapNum = 0.0;
            for (SysAssetNumAnalysisResp resp: respList) {

                // 计算
                sumInNum += resp.getInNum();
                sumOutNum += resp.getOutNum();
                sumScrapNum += resp.getScrapNum();
            }

            double finalSumInNum = sumInNum;
            System.out.println("finalSumInNum：" + finalSumInNum);
            double finalSumOutNum = sumOutNum;
            double finalSumScrapNum = sumScrapNum;
            respList = respList.stream().map(resp -> {
                double percentInNum = finalSumInNum == 0 ? 0: (resp.getInNum() / finalSumInNum) * 100;
                double percentOutNum = finalSumOutNum == 0 ? 0 : (resp.getOutNum() / finalSumOutNum) * 100;
                double percentScrapNum = finalSumScrapNum == 0 ? 0 : (resp.getScrapNum() / finalSumScrapNum) * 100;

                resp.setPercentInNum(numberInstance.format(percentInNum) + "%");
                resp.setPercentOutNum(numberInstance.format(percentOutNum) + "%");
                resp.setPercentScrapNum(numberInstance.format(percentScrapNum) + "%");
                return resp;
            }).collect(Collectors.toList());

            return respList;

        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public List<SysAssetPurchaseAnalysisResp> assetPurchaseAnalysisList(SysAssetPurchaseAnalysisReq req) {

        try {

            // 返回结果集
            List<SysAssetPurchaseAnalysisResp> respList = new ArrayList<>();

            // 查询条件编辑
            Map<String, Object> map = new HashMap<>();
            map.put("startTime", req.getStartTime());
            map.put("endTime", req.getEndTime());

            // 低值易耗品
            List<Long> ids = sysAssetTypeMapper.idList(ParamsConstant.ASSET_TYPE_CONSUMABLES);
            map.put("ids", ids);
            SysAssetPurchaseAnalysisResp consumAblesResp = sysPurchaseRecordMapper.sts(map);
            respList.add(dealPurchase(3, "低值易耗品", consumAblesResp));

            // 办公资产
            ids = sysAssetTypeMapper.idList(ParamsConstant.ASSET_TYPE_OFFICE);
            map.put("ids", ids);
            SysAssetPurchaseAnalysisResp officeResp = sysPurchaseRecordMapper.sts(map);
            respList.add(dealPurchase(2, "办公资产", officeResp));

            // 固定资产
            ids = sysAssetTypeMapper.idList(ParamsConstant.ASSET_TYPE_FIXED);
            map.put("ids", ids);
            SysAssetPurchaseAnalysisResp fixedResp = sysPurchaseRecordMapper.sts(map);
            respList.add(dealPurchase(1, "固定资产", fixedResp));

            return respList;

        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public List<SysAssetReceiveAnalysisResp> assetReceiveAnalysisList(SysAssetReceiveAnalysisReq req) {

        try {

            // 返回结果集
            List<SysAssetReceiveAnalysisResp> respList = new ArrayList<>();

            // 查询条件编辑
            Map<String, Object> map = new HashMap<>();
            map.put("startTime", req.getStartTime());
            map.put("endTime", req.getEndTime());
            map.put("departmentIds", req.getDepartmentIds());
            List<Long> ids = new ArrayList<>();

            // 办公资产
            ids = sysAssetTypeMapper.idList(ParamsConstant.ASSET_TYPE_OFFICE);
            map.put("ids", ids);
            List<SysAssetReceiveAnalysis> officeSts = sysReceiveRecordMapper.sts(map);
            SysAssetReceiveAnalysisResp officeResp = new SysAssetReceiveAnalysisResp();
            officeResp.setSysAssetReceiveAnalysisList(officeSts);
            officeResp.setAssetType(ParamsConstant.ASSET_TYPE_OFFICE.intValue());
            officeResp.setAssetTypeName("办公资产");

            if (req.getAssetType() ==2)
                respList.add(officeResp);


            // 固定资产
            ids = sysAssetTypeMapper.idList(ParamsConstant.ASSET_TYPE_FIXED);
            map.put("ids", ids);
            List<SysAssetReceiveAnalysis> fixedSts = sysReceiveRecordMapper.sts(map);
            SysAssetReceiveAnalysisResp fixedResp = new SysAssetReceiveAnalysisResp();
            fixedResp.setSysAssetReceiveAnalysisList(fixedSts);
            fixedResp.setAssetType(ParamsConstant.ASSET_TYPE_FIXED.intValue());
            fixedResp.setAssetTypeName("固定资产");
            if (req.getAssetType() == 1)
                respList.add(fixedResp);

            // 固定 + 办公
            SysAssetReceiveAnalysisResp stsResp = new SysAssetReceiveAnalysisResp();
            List<SysAssetReceiveAnalysis> sts = new ArrayList<>();
            if (fixedSts.size() == officeSts.size()) {
                for (int i = 0; i < officeSts.size(); i++) {

                    for (int j = 0; j < fixedSts.size(); j++) {

                        if (fixedSts.get(j).getDepartmentId().equals(officeSts.get(i).getDepartmentId())) {
                            SysAssetReceiveAnalysis sysAssetReceiveAnalysis = new SysAssetReceiveAnalysis();
                            sysAssetReceiveAnalysis.setAmount(officeSts.get(i).getAmount().add(fixedSts.get(j).getAmount()));
                            sysAssetReceiveAnalysis.setReceiveNum(officeSts.get(i).getReceiveNum() + fixedSts.get(j).getReceiveNum());
                            sysAssetReceiveAnalysis.setDepartmentId(officeSts.get(i).getDepartmentId());
                            sysAssetReceiveAnalysis.setDepartmentName(officeSts.get(i).getDepartmentName());
                            sts.add(sysAssetReceiveAnalysis);
                            break;
                        }
                    }
                }
            }
            stsResp.setSysAssetReceiveAnalysisList(sts);
            stsResp.setAssetTypeName("固定 + 办公");
            stsResp.setAssetType(0);
            if (req.getAssetType() == 0)
                respList.add(stsResp);
            return respList;

        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public SysPendingAuditStsResp getPendingAuditSts() {

        try {

            String token = httpServletRequest.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(token);
            String userId = String.valueOf(claims.get("id"));
//            SysUser userInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));
            SysPendingAuditStsResp sysPendingAuditStsResp = new SysPendingAuditStsResp();
            int applicationNum = sysApplicationRecordMapper.selectCount(new QueryWrapper<SysApplicationRecord>()
                    .eq("flow_path", userId));

            int receiveNum = sysReceiveRecordMapper.selectCount(new QueryWrapper<SysReceiveRecord>()
                    .eq("flow_path", userId));

            int returnNum = sysReturnRecordMapper.selectCount(new QueryWrapper<SysReturnRecord>()
                    .eq("flow_path", userId));

            int scrapNum = sysChargeOffRecordMapper.selectCount(new QueryWrapper<SysChargeOffRecord>()
                    .eq("flow_path", userId));

            int totalNum = applicationNum + receiveNum + returnNum + scrapNum;
            sysPendingAuditStsResp.setApplicationNum(applicationNum);
            sysPendingAuditStsResp.setReceiveNum(receiveNum);
            sysPendingAuditStsResp.setReturnNum(returnNum);
            sysPendingAuditStsResp.setScrapNum(scrapNum);
            sysPendingAuditStsResp.setTotalNum(totalNum);
            return sysPendingAuditStsResp;
        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }


    /**
     * 采购记录处理
     * @param assetType
     * @param assetTypeName
     * @return
     */
    public SysAssetPurchaseAnalysisResp dealPurchase(Integer assetType, String assetTypeName, SysAssetPurchaseAnalysisResp sysAssetPurchaseAnalysisResp) {
        SysAssetPurchaseAnalysisResp resp = new SysAssetPurchaseAnalysisResp();
        if (null == sysAssetPurchaseAnalysisResp) {
            resp.setAmount(BigDecimal.ZERO);
            resp.setPurchaseNum(0);
        } else {
            CopyUtils.copyProperties(sysAssetPurchaseAnalysisResp, resp);
        }
        resp.setAssetType(assetType);
        resp.setAssetTypeName(assetTypeName);
        return resp;
    }

    /**
     * 查询为空情况下数据装载
     * @param req
     * @return
     */
    public List<SysAssetStatusAnalysis> nullReload(SysAssetStatusAnalysisReq req) {

        List<SysAssetStatusAnalysis> assetStatusAnalyses = new ArrayList<>();
        SysAssetStatusAnalysis assetStatusAnalysisOffice = new SysAssetStatusAnalysis();
        assetStatusAnalysisOffice.setNumPercent("0%");
        assetStatusAnalysisOffice.setNum(0);
        assetStatusAnalysisOffice.setAssetStatus(req.getInventoryStatus());
        assetStatusAnalysisOffice.setAmount(BigDecimal.ZERO);
        assetStatusAnalysisOffice.setAmountPercent("0%");
        assetStatusAnalyses.add(assetStatusAnalysisOffice);
        return assetStatusAnalyses;
    }

    /**
     * 非空情况下数据装载
     * @param assetStatusAnalysis
     * @param sum
     * @param req
     * @return
     */
    public List<SysAssetStatusAnalysis> notNullReload(SysAssetStatusAnalysis assetStatusAnalysis, Integer sum, SysAssetStatusAnalysisReq req) {

        List<SysAssetStatusAnalysis> assetStatusAnalyses = new ArrayList<>();
        double numPercent =((double)assetStatusAnalysis.getNum() / sum) * 100;
        assetStatusAnalysis.setAssetStatus(req.getInventoryStatus());

        // 数据处理
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMaximumFractionDigits(1);
        String format = numberInstance.format(numPercent);
        assetStatusAnalysis.setNumPercent(format);
        assetStatusAnalyses.add(assetStatusAnalysis);

        return assetStatusAnalyses;
    }
}
