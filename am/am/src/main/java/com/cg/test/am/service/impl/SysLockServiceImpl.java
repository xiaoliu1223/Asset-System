package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.vo.request.SysLockReq;
import com.cg.test.am.mapper.SysAssetMapper;
import com.cg.test.am.mapper.SysLockMapper;
import com.cg.test.am.mapper.SysUserMapper;
import com.cg.test.am.model.SysAsset;
import com.cg.test.am.model.SysLock;
import com.cg.test.am.model.SysUser;
import com.cg.test.am.service.SysLockService;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.response.SysLockResp;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class SysLockServiceImpl implements SysLockService {


    @Resource
    SysLockMapper sysLockMapper;

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    HttpServletRequest request;

    @Resource
    SysAssetMapper sysAssetMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void switchAssetLock(SysLockReq req) {

        try {

            SysLock sysLockInfo = sysLockMapper.selectById(1);
            if (null == sysLockInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));

            sysLockInfo.setIsLock(req.getIsLock());
            sysLockInfo.setUpdateBy(sysUserInfo.getId());
            sysLockInfo.setUpdateTime(System.currentTimeMillis());

            sysLockMapper.updateById(sysLockInfo);

            if (req.getIsLock().equals(ParamsConstant.ASSET_CHECK_STATUS_ON)) {

                // 将库存中资产状态全部变更为未盘点状态
                sysAssetMapper.update(null, new UpdateWrapper<SysAsset>()
                        .set("check_status", ParamsConstant.NOINVENTORY).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            }

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public SysLockResp getLockStatus() {

        try {
            SysLock sysLockInfo = sysLockMapper.selectById(1);
            SysLockResp sysLockResp = new SysLockResp();
            sysLockResp.setIsLock(sysLockInfo.getIsLock());
            return sysLockResp;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }
}
