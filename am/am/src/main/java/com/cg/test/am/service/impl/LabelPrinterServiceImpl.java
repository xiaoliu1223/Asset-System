package com.cg.test.am.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.*;
import com.cg.test.am.model.SysAsset;
import com.cg.test.am.model.SysAssetType;
import com.cg.test.am.model.SysDepartment;
import com.cg.test.am.model.SysReceiveRecord;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.LabelPrinterService;
import com.cg.test.am.utils.DrawUtils;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.LabelPrinterReq;
import com.cg.test.am.vo.response.LabelPrinterResp;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LabelPrinterServiceImpl implements LabelPrinterService {


    // 扫码跳转的页面
    private String url = "";

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysReceiveRecordMapper sysReceiveRecordMapper;

    @Resource
    SysDepartmentMapper sysDepartmentMapper;

    @Resource
    SysAssetMapper sysAssetMapper;

    @Resource
    SysAssetTypeMapper sysAssetTypeMapper;

    @Override
    public LabelPrinterResp generatorQrCode(LabelPrinterReq req, Long userId) {

        try {


            // 判断资产类型，如果是低值易耗品不打印标签
            SysAsset sysAsset = sysAssetMapper.selectOne(new QueryWrapper<SysAsset>().eq("asset_code", req.getAssetCode()));
            SysAssetType sysAssetType = sysAssetTypeMapper.selectById(sysAsset.getAssetType());
            if (sysAssetType.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)) {
                throw new ChorBizException(AmErrorCode.ASSET_TYPE_PRINT_ERROR);
            }

            SysReceiveRecord sysReceiveRecordInfo = sysReceiveRecordMapper.selectOne(new QueryWrapper<SysReceiveRecord>()
                    .eq("asset_code", req.getAssetCode()).and(query-> {
                        query.eq("status", ParamsConstant.RECEIVE_STATUS_RECEIVE).or().eq("status",ParamsConstant.RECEIVE_STATUS_INTHEBACK);
                    }));

            String words= req.getAssetCode();
            if (null != sysReceiveRecordInfo) {
                SysDepartment sysDepartmentInfo = sysDepartmentMapper.selectById(sysReceiveRecordInfo.getDepartmentId());
                words = words + "&" + sysDepartmentInfo.getName() + "&" + sysReceiveRecordInfo.getUsedBy();
            }

            QrConfig qrConfig = new QrConfig(110, 110);
            qrConfig.setErrorCorrection(ErrorCorrectionLevel.H); // 高纠错级别
            String filePath = "/home/picture/assets-manager/zing/" + req.getAssetCode() + ".jpg";
//            String filePath = "D:\\upload\\zxing\\" + req.getAssetCode() + ".jpg";
//            QrCodeUtil.generate(url + "/sysAsset/getAssetDetailByAssetCode?assetCode=" + req.getAssetCode(), qrConfig, FileUtil.file(filePath));
            QrCodeUtil.generate(req.getAssetCode(), qrConfig, FileUtil.file(filePath));
            DrawUtils drawUtils = new DrawUtils();

            String imageBase64 = drawUtils.graphicsGeneration("/home/picture/assets-manager/logo/logo.jpg", filePath, words, "/home/picture/assets-manager/out/");
//            String imageBase64 = drawUtils.graphicsGeneration("D:\\upload\\logo\\logo.jpg", filePath, words, "D:\\upload\\out\\");
            LabelPrinterResp labelPrinterResp = new LabelPrinterResp();
            labelPrinterResp.setImageBase64(imageBase64);

            return labelPrinterResp;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }
}
