package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.SysAssetMapper;
import com.cg.test.am.mapper.SysPurchaseRecordMapper;
import com.cg.test.am.mapper.SysUserMapper;
import com.cg.test.am.model.SysAsset;
import com.cg.test.am.model.SysPurchaseRecord;
import com.cg.test.am.model.SysUser;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysPurchaseRecordService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.SysPurchaseConfirmReq;
import com.cg.test.am.vo.request.SysPurchaseRecordListReq;
import com.cg.test.am.vo.response.SysPurchaseRecordListResp;
import com.cg.test.am.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SysPurchaseRecordServiceImpl implements SysPurchaseRecordService {

    @Resource
    SysPurchaseRecordMapper sysPurchaseRecordMapper;
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    SysAssetMapper sysAssetMapper;

    @Resource
    HttpServletRequest request;

    @Override
    public Map<String,Object> list(SysPurchaseRecordListReq req){
        try{

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
//            String departmentIds = claims.get("fillArgs").toString();
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));

            Map<String, Object> map = new HashMap<String, Object>();

            // 非综合部无权查看采购记录
            if (!sysUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)) {
                map.put("total_record", 0);
                map.put("data", new ArrayList<>());
                return map;
            }
            if(req.getDepartmentId() == null){
                if (sysUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)) {
                    req.setDepartmentIds(null);    // 综合部的可以查看所有部门的资产信息
                } else {
                    String departmentIds = claims.get("fillArgs").toString();
                    req.setDepartmentIds(departmentIds);
                }
            }else{
                req.setDepartmentIds(req.getDepartmentId());
            }
            //req.setDepartmentIds(null);

            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysPurchaseRecordMapper.count(req);
            List<SysPurchaseRecordListResp> list = sysPurchaseRecordMapper.list(req, offset, limit);
            map.put("total_record", count);
            map.put("data", list);
            return map;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirm(SysPurchaseConfirmReq req, Long id){
        try{
            //查询下是否存在
            SysPurchaseRecord sysPurchaseRecord = sysPurchaseRecordMapper.selectByPrimaryKey(id);

            if (null == sysPurchaseRecord) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }else if(sysPurchaseRecord.getPurchaseStatus().equals(ParamsConstant.BUY_SUCCESS)){
                throw new ChorBizException(AmErrorCode.STATUS_NO_CHANGE);
            }

            SysUser sysUserInfo = sysUserMapper.selectById(req.getConfirmUserId());

            //判断所属部门是否是综合管理部并且是资产管理员
            if(!(sysUserInfo.getPostId().equals(ParamsConstant.POST_GROUP_ASSET_MANAGER)|| sysUserInfo.getPostId().equals(ParamsConstant.POST_GENERAL_ASSET_MANAGER))){
                throw new ChorBizException(AmErrorCode.NO_AUTHORIZATION);
            }

            CopyUtils.copyProperties(req,sysPurchaseRecord);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String formatTime = simpleDateFormat.format(new Date());
            int random = (int)(Math.random()*900)+100;
            // todo 生成规则
            sysPurchaseRecord.setAssetCode(formatTime+random);
            sysPurchaseRecord.setPurchaseStatus(ParamsConstant.BUY_SUCCESS);
            sysPurchaseRecord.setBuyer(req.getConfirmUserId());
            sysPurchaseRecord.setBuyerName(sysUserInfo.getUsername());
            //修改实际采购情况
            sysPurchaseRecordMapper.updateById(sysPurchaseRecord);

            //新增入库
            SysAsset sysAsset = new SysAsset();
            sysAsset.setAssetCode(sysPurchaseRecord.getAssetCode());
            sysAsset.setAssetType(req.getActualAssetType());
            sysAsset.setAssetName(req.getActualAssetName());
            sysAsset.setAssetNum(req.getActualNum());
            sysAsset.setUnits(req.getActualUnits());
            sysAsset.setPrice(req.getActualPrice());
            sysAsset.setDepartmentId(sysPurchaseRecord.getDepartmentId());
            sysAsset.setCheckStatus(ParamsConstant.DEL_FLAG_DEFAULT);
            sysAsset.setInventoryStatus(ParamsConstant.ASSET_INVENTORY_STATUS_IN);
            sysAsset.setAssetStatus(ParamsConstant.ASSET_STATUS_GOOD);
            sysAsset.setDelFlag(ParamsConstant.DEL_FLAG_DEFAULT);
            sysAsset.setCreateTime(System.currentTimeMillis());
            sysAsset.setSpecification(req.getActualSpecification());
            sysAsset.setUserId(req.getConfirmUserId());
            sysAsset.setUsername(sysUserInfo.getUsername());
            sysAssetMapper.insertSelective(sysAsset);
        }catch(ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }
}
