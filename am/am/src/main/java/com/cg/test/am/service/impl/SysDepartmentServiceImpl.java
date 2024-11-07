package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.SysDepartmentMapper;
import com.cg.test.am.mapper.SysUserMapper;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.request.SysDeparymentListReq;
import com.cg.test.am.vo.request.SysDeparymentReq;
import com.cg.test.am.vo.response.SysDeparymentResp;
import com.cg.test.am.model.SysDepartment;
import com.cg.test.am.service.SysDepartmentService;
import com.cg.test.am.utils.JwtUtil;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.response.SysTree;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysDepartmentServiceImpl implements SysDepartmentService {
    @Resource
    SysDepartmentMapper sysDepartmentMapper;

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    HttpServletRequest request;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysDepartment sysDepartment) {

        try {

            int count = sysDepartmentMapper.selectCount(new QueryWrapper<SysDepartment>().eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).eq("name", sysDepartment.getName()));
            if (count > 0) {
                throw new ChorBizException(AmErrorCode.SOURCE_EXIST);
            }
            //验证上级部门是否存在
            Integer existCount = sysDepartmentMapper.selectCount(new QueryWrapper<SysDepartment>().eq("id", sysDepartment.getPid()));
            if (existCount == 0) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            sysDepartment.setCreateTime(System.currentTimeMillis());
            sysDepartment.setDelFlag(ParamsConstant.DEL_FLAG_DEFAULT);
            sysDepartmentMapper.insertSelective(sysDepartment);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public Map<String, Object> list(SysDeparymentListReq req) {

        try {
            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysDepartmentMapper.count(req);
            List<SysDepartment> list = sysDepartmentMapper.list(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public SysDeparymentResp find(Long id) {

        try {
            SysDepartment info = sysDepartmentMapper.selectByPrimaryKey(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            SysDeparymentResp sysDeparymentResp = new SysDeparymentResp();
            CopyUtils.copyProperties(info, sysDeparymentResp);
            return sysDeparymentResp;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public void modify(SysDeparymentReq req, Long id) {

        try {

            SysDepartment info = sysDepartmentMapper.selectByPrimaryKey(id);

            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            if(info.getId().equals(req.getPid())){
                throw new ChorBizException(AmErrorCode.ASSET_TYPE_RELATIONSHIP_ERROR);
            }

            CopyUtils.copyProperties(req, info);
            long updateTime = System.currentTimeMillis();
            info.setUpdateTime(updateTime);
            sysDepartmentMapper.updateByPrimaryKey(info);

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

            SysDepartment info = sysDepartmentMapper.selectByPrimaryKey(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            SysDepartment res = new SysDepartment();
            res.setId(id);
            res.setDelFlag(ParamsConstant.DEL_FLAG_DELETED);
            sysDepartmentMapper.updateById(res);

            // 编辑下属部门得上级部门
            sysDepartmentMapper.update(null, new UpdateWrapper<SysDepartment>()
                    .set("pid",info.getPid()).eq("pid", info.getId()));

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public List<SysTree>  treeList(Integer id){
        List<SysDepartment> list = sysDepartmentMapper.selectList(new QueryWrapper<SysDepartment>().eq("del_flag",ParamsConstant.DEL_FLAG_DEFAULT));
        List<SysTree> resList = new ArrayList<>();
        // 用户Id不等于0,查询该用户拥有的部门id,设置树结构默认选中
        if(id!=ParamsConstant.DEL_FLAG_DEFAULT){
            String departmentIds = sysDepartmentMapper.selectByIdGetDep(id);
            String[] strArray = departmentIds.split(",");
            for (SysDepartment sd: list) {
                SysTree resp = new SysTree();
                if (sd.getPid() == 0) {

                    for(Integer i=0;i<strArray.length;i++){
                        if(Long.valueOf(strArray[i])==sd.getId()){
                            resp.setSelect(ParamsConstant.SELECT);
                        }
                    }

                    resp.setId(sd.getId());
                    resp.setName(sd.getName());
                    resp.setPid(sd.getPid());
                    resp.setDescription(sd.getDescription());
                    resp.setList(getChildListSetSelect(sd.getId(), list,strArray));
                    resList.add(resp);
                }
            }
        }else{
            for (SysDepartment sd: list) {
                SysTree resp = new SysTree();
                if (sd.getPid() == 0) {
                    resp.setId(sd.getId());
                    resp.setName(sd.getName());
                    resp.setList(getChildList(sd.getId(), list));
                    resp.setPid(sd.getPid());
                    resp.setDescription(sd.getDescription());
                    resList.add(resp);
                }
            }
        }

        return resList;
    }

    @Override
    public List<SysDepartment> listByUser() {
        String authorization = request.getHeader("Authorization");
        Claims claims = JwtUtil.parseJwt(authorization);
        Integer id = Integer.valueOf(claims.get("id").toString());
        List<SysDepartment> list = sysDepartmentMapper.listByUser(id);
        return list;
    }

    List<SysTree> getChildList(Long pid, List<SysDepartment> list) {

        List<SysTree> resList = new ArrayList<>();
        for (SysDepartment sd: list) {
            SysTree resp = new SysTree();
            if (sd.getPid() == pid) {
                resp.setId(sd.getId());
                resp.setName(sd.getName());
                resp.setList(getChildList(sd.getId(), list));
                resp.setPid(sd.getPid());
                resp.setDescription(sd.getDescription());
                resList.add(resp);
            }
        }
        return resList;
    }

    List<SysTree> getChildListSetSelect(Long pid, List<SysDepartment> list,String[] strArray) {

        List<SysTree> resList = new ArrayList<>();
        for (SysDepartment sd: list) {
            SysTree resp = new SysTree();
            if (sd.getPid() == pid) {
                for(Integer i=0;i<=strArray.length;i++){
                    if(Long.valueOf(strArray[i])==sd.getId()){
                        resp.setSelect(ParamsConstant.SELECT);
                    }
                }
                resp.setId(sd.getId());
                resp.setName(sd.getName());
                resp.setList(getChildList(sd.getId(), list));
                resp.setPid(sd.getPid());
                resp.setDescription(sd.getDescription());
                resList.add(resp);
            }
        }
        return resList;
    }

}
