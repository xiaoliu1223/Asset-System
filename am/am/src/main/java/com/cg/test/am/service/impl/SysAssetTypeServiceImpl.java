package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.SysAssetTypeMapper;
import com.cg.test.am.model.SysAssetType;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysAssetTypeService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.SysAssetTypeListReq;
import com.cg.test.am.vo.request.SysAssetTypeReq;
import com.cg.test.am.vo.response.SysAssetTypeResp;
import com.cg.test.am.vo.response.SysTree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysAssetTypeServiceImpl implements SysAssetTypeService {
    @Resource
    SysAssetTypeMapper sysAssetTypeMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysAssetType sysAssetType) {

        try {

            int count = sysAssetTypeMapper.selectCount(new QueryWrapper<SysAssetType>()
                    .eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).eq("name", sysAssetType.getName()));
            if (count > 0) {
                throw new ChorBizException(AmErrorCode.SOURCE_EXIST);
            }

            sysAssetType.setCreateTime(System.currentTimeMillis());
            sysAssetType.setDelFlag(ParamsConstant.DEL_FLAG_DEFAULT);


            Boolean flag = true;
            Long pid = sysAssetType.getPid();
            while(flag) {
                SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(pid);
                if (null != sysAssetTypeInfo) {

                    if (sysAssetTypeInfo.getPid() == 0) {
                        sysAssetType.setSuperId(pid);
                        flag = false;
                    }  else {
                        pid = sysAssetTypeInfo.getPid();
                    }

                } else {
                    sysAssetType.setSuperId(pid);
                    flag = false;
                }
            }


            sysAssetTypeMapper.insertSelective(sysAssetType);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public Map<String, Object> list(SysAssetTypeListReq req) {

        try {
            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysAssetTypeMapper.count(req);
            List<SysAssetType> list = sysAssetTypeMapper.list(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public SysAssetTypeResp find(Long id) {

        try {
            SysAssetType info = sysAssetTypeMapper.selectByPrimaryKey(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            SysAssetTypeResp sysAssetTypeResp = new SysAssetTypeResp();
            CopyUtils.copyProperties(info, sysAssetTypeResp);
            return sysAssetTypeResp;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(SysAssetTypeReq req, Long id) {

        try {

            SysAssetType info = sysAssetTypeMapper.selectByPrimaryKey(id);

            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            if (req.getPid().equals(info.getId())) {

                throw new ChorBizException(AmErrorCode.ASSET_TYPE_RELATIONSHIP_ERROR);
            }

            Boolean flag = true;
            Long pid = req.getPid();
            while(flag) {
                SysAssetType sysAssetTypeInfo = sysAssetTypeMapper.selectById(pid);
                if (null != sysAssetTypeInfo) {

                    if (sysAssetTypeInfo.getPid() == 0) {
                        info.setSuperId(pid);
                        flag = false;
                    }  else {
                        pid = sysAssetTypeInfo.getPid();
                    }

                } else {
                    info.setSuperId(pid);
                    flag = false;
                }
            }

            CopyUtils.copyProperties(req, info);
            long updateTime = System.currentTimeMillis();
            info.setUpdateTime(updateTime);
            sysAssetTypeMapper.updateByPrimaryKey(info);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(Long id) {
        try {

            SysAssetType info = sysAssetTypeMapper.selectByPrimaryKey(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            info.setDelFlag(ParamsConstant.DEL_FLAG_DELETED);
            info.setUpdateTime(System.currentTimeMillis());
            sysAssetTypeMapper.updateById(info);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public List<SysTree>  treeList(){
        List<SysAssetType> list = sysAssetTypeMapper.selectList(new QueryWrapper<SysAssetType>().eq("del_flag",ParamsConstant.DEL_FLAG_DEFAULT));
        List<SysTree> resList = new ArrayList<>();

        for (SysAssetType ls: list) {
            SysTree resp = new SysTree();
            if (ls.getPid() == 0) {
                resp.setId(ls.getId());
                resp.setName(ls.getName());
                resp.setList(getChildList(ls.getId(), list));
                resp.setPid(ls.getPid());
                resList.add(resp);
            }
        }
        return resList;
    }

    List<SysTree> getChildList(Long pid, List<SysAssetType> list) {

        List<SysTree> resList = new ArrayList<>();
        for (SysAssetType csw: list) {
            SysTree resp = new SysTree();
            if (csw.getPid() == pid) {
                resp.setId(csw.getId());
                resp.setName(csw.getName());
                resp.setList(getChildList(csw.getId(), list));
                resp.setPid(csw.getPid());
                resList.add(resp);
            }
        }
        return resList;
    }

}
