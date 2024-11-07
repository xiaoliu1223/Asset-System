package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.model.SysCheckLog;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.vo.response.SysCheckLogListResp;
import com.cg.test.am.vo.request.SysCheckLogListReq;
import com.cg.test.am.vo.request.SysCheckLogReq;
import com.cg.test.am.mapper.SysAssetMapper;
import com.cg.test.am.mapper.SysCheckLogMapper;
import com.cg.test.am.mapper.SysLockMapper;
import com.cg.test.am.mapper.SysUserMapper;
import com.cg.test.am.model.SysAsset;
import com.cg.test.am.model.SysLock;
import com.cg.test.am.model.SysUser;
import com.cg.test.am.service.SysCheckLogService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.response.SysCheckLogResp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysCheckLogServiceImpl implements SysCheckLogService {

    @Resource
    SysCheckLogMapper sysCheckLogMapper;
    @Resource
    SysAssetMapper sysAssetMapper;
    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysLockMapper sysLockMapper;

    @Override
    public Map<String,Object> list(SysCheckLogListReq req){
        try{
            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysCheckLogMapper.count(req);
            List<SysCheckLogListResp> list = sysCheckLogMapper.list(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysCheckLogReq req){
        try{

            // 判断是否已经开启资产盘点开关
            SysLock sysLockInfo = sysLockMapper.selectById(1);
            if (sysLockInfo.getIsLock().equals(ParamsConstant.ASSET_CHECK_STATUS_OFF)) {
                throw new ChorBizException(AmErrorCode.ASSET_CHECK_LOCK_OFF);
            }

            SysCheckLog sysCheckLog = new SysCheckLog();
            CopyUtils.copyProperties(req, sysCheckLog);

            //根据用户id 查询用户姓名
            SysUser sysUserInfo = sysUserMapper.selectById(sysCheckLog.getCheckBy());

            //根据资产编号查询资产信息
            SysAsset sysAsset = sysAssetMapper.selectOne(new QueryWrapper<SysAsset>()
                    .eq("asset_code", sysCheckLog.getAssetCode()).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            if(sysAsset == null){
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }else if(sysAsset.getCheckStatus().equals(ParamsConstant.HAVEINVENTORY)){
                throw new ChorBizException(AmErrorCode.HAVE_INVENTORY);
            }
            Long now = System.currentTimeMillis();

            //将资产设为盘点状态
            sysAsset.setCheckStatus(ParamsConstant.HAVEINVENTORY);
            sysAsset.setAssetStatus(req.getAssetStatus());
            sysAsset.setUpdateTime(now);
            sysAssetMapper.updateByPrimaryKeySelective(sysAsset);

            //添加盘点日志
            sysCheckLog.setCheckName(sysUserInfo.getUsername());
            sysCheckLog.setCheckTime(now);
            sysCheckLogMapper.insert(sysCheckLog);

        }catch (ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public List<SysCheckLogResp> detail(String assetCode){
        try{
            List<SysCheckLog> sysCheckLog = sysCheckLogMapper.selectList(new QueryWrapper<SysCheckLog>().eq("asset_code", assetCode));
            if(sysCheckLog.size()==0){
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            SysCheckLogResp sysCheckLogResp = new SysCheckLogResp();
            List<SysCheckLogResp> sysCheckLogRespList = new ArrayList<>();
            for (SysCheckLog sc:sysCheckLog){
                CopyUtils.copyProperties(sc,sysCheckLogResp);
                sysCheckLogRespList.add(sysCheckLogResp);
            }
            return sysCheckLogRespList;
        }catch(ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

}
