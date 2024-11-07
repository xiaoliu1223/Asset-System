package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.SysAssetMapper;
import com.cg.test.am.mapper.SysDepartmentMapper;
import com.cg.test.am.mapper.SysUserMapper;
import com.cg.test.am.model.SysAsset;
import com.cg.test.am.model.SysAssetType;
import com.cg.test.am.model.SysDepartment;
import com.cg.test.am.model.SysUser;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysAssetService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.utils.ExcelUtil;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.SysAssetCustomizedListReq;
import com.cg.test.am.vo.request.SysAssetListReq;
import com.cg.test.am.vo.response.SysAssetResp;
import com.cg.test.am.mapper.SysAssetTypeMapper;
import com.cg.test.am.vo.request.SysAssetReq;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SysAssetServiceImpl implements SysAssetService {

    @Resource
    SysAssetMapper sysAssetMapper;

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysAssetTypeMapper sysAssetTypeMapper;

    @Resource
    SysDepartmentMapper sysDepartmentMapper;

    @Resource
    HttpServletRequest request;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void  save(SysAsset sysAsset){
        try{

            SysUser sysUserInfo = sysUserMapper.selectById(sysAsset.getUserId());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String formatTime = simpleDateFormat.format(new Date());
            int random = (int)(Math.random()*900)+100;
            sysAsset.setAssetCode(formatTime+random);
            //根据类型id查询最上级id 只有低值易耗品 数量才能存在多个
            SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(sysAsset.getAssetType());

            if(!sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)
                    && sysAsset.getAssetNum()>1){
                throw new ChorBizException(AmErrorCode.NUM_ABNORMAL);
            }

            sysAsset.setUsername(sysUserInfo.getUsername()); // 录入人员，或者采购人员姓名
            sysAsset.setCreateTime(System.currentTimeMillis());  // 录入时间、采购完成时间
            sysAsset.setCheckStatus(ParamsConstant.DEL_FLAG_DEFAULT);
            sysAsset.setInventoryStatus(ParamsConstant.ASSET_INVENTORY_STATUS_IN);
            sysAsset.setAssetStatus(ParamsConstant.ASSET_STATUS_GOOD);
            sysAsset.setDelFlag(ParamsConstant.DEL_FLAG_DEFAULT);
            sysAssetMapper.insertSelective(sysAsset);

        }catch (ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public Map<String,Object> list(SysAssetListReq req){

        try {

            String authorization = request.getHeader("Authorization");
            Claims claims = JwtUtil.parseJwt(authorization);
            SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));

            if(req.getDepartmentId() == null){
                if (sysUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)) {
                    req.setDepartmentIds(null);    // 综合部的可以查看所有部门的资产信息
                } else {
                    String departmentIds = claims.get("fillArgs").toString();
                    req.setDepartmentIds(departmentIds);
                }
            }

            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            Long count = sysAssetMapper.count(req);
            List<SysAssetResp> list = sysAssetMapper.list(req, offset, limit);
            Long now = System.currentTimeMillis();
            for (SysAssetResp sa:list){
                //使用年限计算，归还时间减去领用时间
                double difference = (double) (now - sa.getCreateTime() ) / 1000;
                double d = difference / 86400 / 365;
                Integer usedAge = (int) Math.ceil(d);
                sa.setUsedAge(usedAge+"年");
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("total_record", count);
            map.put("data", list);
            return map;

        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(SysAssetReq req, Long id){
        try{

            //判断修改id是否存在
            Integer count = sysAssetMapper.selectCount(new QueryWrapper<SysAsset>().eq("id", id).eq("del_flag", 0));
            if (count == 0) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            //根据类型id查询最上级id 只有低值易耗品 数量才能存在多个
            SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(req.getAssetType());
            if(!sysAssetTypeInfo.getSuperId().equals(ParamsConstant.ASSET_TYPE_CONSUMABLES)
                    && req.getAssetNum()>1){
                throw new ChorBizException(AmErrorCode.NUM_ABNORMAL);
            }
            SysAsset sysAsset = new SysAsset();
            CopyUtils.copyProperties(req, sysAsset);
            sysAsset.setId(id);
            sysAsset.setUpdateTime(System.currentTimeMillis());
            sysAssetMapper.updateByPrimaryKeySelective(sysAsset);

        }catch (ChorBizException e){
            throw e;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public SysAssetResp find(Long id){

        try {

            SysAssetResp sysAssetResp = sysAssetMapper.getOne(id);
            if(null == sysAssetResp){
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            //使用年限计算，归还时间减去领用时间
            Long now = System.currentTimeMillis();
            double difference = (double) (now - sysAssetResp.getCreateTime() ) / 1000;
            double d = difference / 86400 / 365;
            Integer usedAge = (int) Math.ceil(d);
            sysAssetResp.setUsedAge(usedAge+"年");
            return sysAssetResp;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public void downloadExcel(HttpServletResponse response, String departmentId, String token){
        String authorization = token;
        Claims claims = JwtUtil.parseJwt(authorization);
        String departmentIds = claims.get("fillArgs").toString();
        SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", claims.get("id")));
        //请求参数
        String para_departmentIds = null;
        //部门id为空，查所有拥有权限的部门信息
        if (departmentId==null){
            if (!sysUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)) {
                para_departmentIds = claims.get("fillArgs").toString();
            }
        }else{
            if (sysUserInfo.getDepartmentId().equals(ParamsConstant.DEPARTMENT_GENERAL_MANAGEMENT)||Arrays.asList(departmentIds.split(",")).contains(departmentId)) {
                para_departmentIds = departmentId;
            }else{
                //参数错误
                return;
            }
        }
        List<LinkedHashMap<String, Object>> list = sysAssetMapper.downloadExcel(para_departmentIds);

        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> map = list.get(0);

            String[] headers = new String[map.size()];
            int i = 0;
            for (String key : map.keySet()) {
                headers[i++] = key;
            }
            List<Object[]> datas = new ArrayList<>(list.size());
            for (Map<String, Object> m : list) {
                Object[] objects = new Object[headers.length];
                for (int j = 0; j < headers.length; j++) {
                    objects[j] = m.get(headers[j]);
                }
                datas.add(objects);
            }
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            String str = sdf.format(d);
            ExcelUtil.excelExport("资产信息列表"+str, headers,datas, response);
        }else{
            String[] headers = new String[1];
            headers[0] = "暂无相关数据";
            List<Object[]> datas = new ArrayList<>();
            ExcelUtil.excelExport("资产信息无相关数据", headers,datas, response);
        }
    }

    @Override
    public SysAssetResp getAssetDetailByAssetCode(String assetCode) {

        try {

            SysAsset sysAssetInfo = sysAssetMapper.selectOne(new QueryWrapper<SysAsset>().eq("asset_code", assetCode));
            if(null == sysAssetInfo){
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            SysAssetResp sysAssetResp = new SysAssetResp();
            CopyUtils.copyProperties(sysAssetInfo, sysAssetResp);

            // 获取资产类别
            SysAssetType sysAssetType = sysAssetTypeMapper.selectById(sysAssetInfo.getAssetType());
            sysAssetResp.setAssetTypeName(sysAssetType.getName());

            // 获取所属部门
            SysDepartment sysDepartment = sysDepartmentMapper.selectById(sysAssetInfo.getDepartmentId());
            sysAssetResp.setDepartmentName(sysDepartment.getName());

            //使用年限计算，归还时间减去领用时间
            Long now = System.currentTimeMillis();
            double difference = (double) (now - sysAssetInfo.getCreateTime() ) / 1000;
            double d = difference / 86400 / 365;
            Integer usedAge = (int) Math.ceil(d);
            sysAssetResp.setUsedAge(usedAge+"年");
            return sysAssetResp;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public Map<String, Object> getCustomizedList(SysAssetCustomizedListReq req) {

        try {

            Page<SysAssetResp> page = new Page<>(req.getCurrent(), req.getLimit());

            IPage<SysAssetResp> respList = sysAssetMapper.selectCustomizedPage(page, req);

            Map<String, Object> map = new HashMap<>();
            map.put("total_record", respList.getTotal());
            map.put("data", respList.getRecords());
            return map;
        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

}
