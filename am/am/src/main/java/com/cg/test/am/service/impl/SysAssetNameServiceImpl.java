package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.model.SysAssetName;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysAssetNameService;
import com.cg.test.am.utils.BeanUtils;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.request.SysAssetNameReq;
import com.cg.test.am.vo.response.SysAssetNameResp;
import com.cg.test.am.mapper.SysAssetNameMapper;
import com.cg.test.am.vo.ParamsConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysAssetNameServiceImpl implements SysAssetNameService {

    @Resource
    SysAssetNameMapper sysAssetNameMapper;

    @Override
    public void save(SysAssetNameReq req) {
        try {

            // 查询是否存在已有的
            Integer count = sysAssetNameMapper.selectCount(new QueryWrapper<SysAssetName>().eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).eq("name", req.getName()));
            if (count > 0) {
                throw new ChorBizException(AmErrorCode.SOURCE_EXIST);
            }

            SysAssetName sysAssetName = new SysAssetName();
            CopyUtils.copyProperties(req, sysAssetName);
            sysAssetNameMapper.insertSelective(sysAssetName);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public List<SysAssetNameResp> list() {
        List<SysAssetName> list = sysAssetNameMapper.selectList(new QueryWrapper<SysAssetName>().eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));

        List<SysAssetNameResp> resp = BeanUtils.toList(list, SysAssetNameResp.class);
        return resp;
    }

    @Override
    public void remove(Long id) {
        try {

            // 是否存在
            SysAssetName sysAssetName = sysAssetNameMapper.selectById(id);
            if (null == sysAssetName) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            sysAssetName.setDelFlag(ParamsConstant.DEL_FLAG_DELETED);
            sysAssetNameMapper.updateById(sysAssetName);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }
}
