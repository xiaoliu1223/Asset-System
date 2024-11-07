package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.SysUnitMapper;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.utils.BeanUtils;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.request.SysUnitReq;
import com.cg.test.am.model.SysUnit;
import com.cg.test.am.service.SysUnitService;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.response.SysUnitResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUnitServiceImpl implements SysUnitService {

    @Resource
    SysUnitMapper sysUnitMapper;

    @Override
    public void save(SysUnitReq req) {

        try {

            // 查询是否存在已有的
            Integer count = sysUnitMapper.selectCount(new QueryWrapper<SysUnit>().eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).eq("name", req.getName()));
            if (count > 0) {
                throw new ChorBizException(AmErrorCode.SOURCE_EXIST);
            }

            SysUnit sysUnit = new SysUnit();
            CopyUtils.copyProperties(req, sysUnit);
            sysUnitMapper.insertSelective(sysUnit);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public List<SysUnitResp> list() {

        List<SysUnit> list = sysUnitMapper.selectList(new QueryWrapper<SysUnit>().eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));

        List<SysUnitResp> resp = BeanUtils.toList(list, SysUnitResp.class);
        return resp;
    }

    @Override
    public void remove(Long id) {

        try {

            // 是否存在
            SysUnit sysUnit = sysUnitMapper.selectById(id);
            if (null == sysUnit) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            sysUnit.setDelFlag(ParamsConstant.DEL_FLAG_DELETED);
            sysUnitMapper.updateById(sysUnit);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }
}
