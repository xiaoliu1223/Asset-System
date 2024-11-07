package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.SysPermissionMapper;
import com.cg.test.am.mapper.SysRoleMapper;
import com.cg.test.am.mapper.SysRolePermissionMapper;
import com.cg.test.am.model.SysRolePermission;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysPermissionService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.response.SysPermissionResp;
import com.cg.test.am.model.SysPermission;
import com.cg.test.am.vo.request.SysPermissionReq;
import com.cg.test.am.vo.response.GeneralResp;
import com.cg.test.am.vo.response.RoleHavePermissionResp;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.response.TreePermissionResp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    SysPermissionMapper sysPermissionMapper;
    @Resource
    SysRoleMapper sysRoleMapper;
    @Resource
    SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<TreePermissionResp> treeList(Long roleId) {

        try {
            List<SysPermission> sysPermissions = sysPermissionMapper.selectList(new QueryWrapper<SysPermission>()
                  .eq("status", ParamsConstant.DEL_FLAG_DEFAULT).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).orderByAsc("sort"));
            List<TreePermissionResp> respList = new ArrayList<>();

            // 角色Id不等于0,查询该角色拥有的权限id,设置树结构默认选中
            if(!roleId.equals(ParamsConstant.DEL_FLAG_DEFAULT_VALUE)){

                //根据角色id查询权限
                List<GeneralResp> roleper = sysRoleMapper.listByRoleId(roleId);

                for (SysPermission sysPermission: sysPermissions) {
                    TreePermissionResp treePermissionResp = new TreePermissionResp();
                    if (sysPermission.getPid() == 0) {
                        CopyUtils.copyProperties(sysPermission, treePermissionResp);
                        for(GeneralResp gp:roleper){
                            if(gp.getId().equals(treePermissionResp.getId())){
                                treePermissionResp.setSelect(ParamsConstant.SELECT);
                            }
                        }
                        treePermissionResp.setChildren(getChildrenListSetSelect(sysPermission.getId(), sysPermissions,roleper));
                        respList.add(treePermissionResp);
                    }
                }

            }else{
                for (SysPermission sysPermission: sysPermissions) {
                    TreePermissionResp treePermissionResp = new TreePermissionResp();
                    if (sysPermission.getPid() == 0) {
                        CopyUtils.copyProperties(sysPermission, treePermissionResp);
                        treePermissionResp.setChildren(getChildrenList(sysPermission.getId(), sysPermissions));
                        respList.add(treePermissionResp);
                    }
                }
            }
            return respList;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysPermission sysPermission) {

        try {

            Integer count = sysPermissionMapper.selectCount(new QueryWrapper<SysPermission>()
                    .eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).eq("name", sysPermission.getName()));
            if (count > 0) {
                throw new ChorBizException(AmErrorCode.SOURCE_EXIST);
            }

            long time = System.currentTimeMillis();
            sysPermission.setCreateTime(time);
            sysPermission.setDelFlag(ParamsConstant.DEL_FLAG_DEFAULT);
            sysPermissionMapper.insertSelective(sysPermission);

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

            SysPermission sysPermissionInfo = sysPermissionMapper.selectById(id);
            if (null == sysPermissionInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            sysPermissionInfo.setDelFlag(ParamsConstant.DEL_FLAG_DELETED);
            sysPermissionMapper.updateById(sysPermissionInfo);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(SysPermissionReq req, Long id) {

        try {

            // 判断是否存在
            SysPermission sysPermissionInfo = sysPermissionMapper.selectOne(new QueryWrapper<SysPermission>()
                    .eq("id", id).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            if (null == sysPermissionInfo) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            Integer count = sysPermissionMapper.selectCount(new QueryWrapper<SysPermission>()
                    .eq("name", req.getName()).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            if (count > 1) {
                throw new ChorBizException(AmErrorCode.SOURCE_EXIST);
            }

            CopyUtils.copyProperties(req, sysPermissionInfo);
            sysPermissionMapper.updateById(sysPermissionInfo);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public SysPermissionResp find(Long id) {

        try {

            SysPermission sysPermission = sysPermissionMapper.selectById(id);
            if (null == sysPermission) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            SysPermissionResp sysPermissionResp = new SysPermissionResp();
            CopyUtils.copyProperties(sysPermission, sysPermissionResp);
            SysPermission sysPermissionInfo = sysPermissionMapper.selectById(sysPermission.getPid());
            if (null != sysPermissionInfo) {
                sysPermissionResp.setParentName(sysPermissionInfo.getName());
            }
            return sysPermissionResp;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }


    public List<TreePermissionResp> getChildrenList(Long pid, List<SysPermission> list) {

        List<TreePermissionResp> respList = new ArrayList<>();
        for (SysPermission sysPermission: list) {
            TreePermissionResp treePermissionResp = new TreePermissionResp();
            if (sysPermission.getPid().equals(pid) ) {
                CopyUtils.copyProperties(sysPermission, treePermissionResp);
                treePermissionResp.setChildren(getChildrenList(sysPermission.getId(), list));
                respList.add(treePermissionResp);
            }
        }
        return respList;
    }

    public List<TreePermissionResp> getChildrenListSetSelect(Long pid, List<SysPermission> list, List<GeneralResp> roleper) {

        List<TreePermissionResp> respList = new ArrayList<>();
        for (SysPermission sysPermission: list) {
            TreePermissionResp treePermissionResp = new TreePermissionResp();
            if (sysPermission.getPid().equals(pid)) {
                CopyUtils.copyProperties(sysPermission, treePermissionResp);
                for(GeneralResp gp:roleper){
                    if(gp.getId().equals(treePermissionResp.getId())){
                        treePermissionResp.setSelect(1);
                    }
                }
                treePermissionResp.setChildren(getChildrenListSetSelect(sysPermission.getId(), list, roleper));
                respList.add(treePermissionResp);
            }
        }
        return respList;
    }

    @Override
    public List<RoleHavePermissionResp> getTreeListByRoleId(List<Long> roleIds){

        List<SysPermission> sysPermissions = sysPermissionMapper.selectList(new QueryWrapper<SysPermission>()
                .eq("status", ParamsConstant.DEL_FLAG_DEFAULT).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).orderByAsc("sort"));
        List<RoleHavePermissionResp> respList = new ArrayList<>();
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", roleIds);
        //根据角色id查询权限
        List<SysRolePermission> rolesPer = sysRolePermissionMapper.selectList(queryWrapper);

        //多个角色可能存在权限冲突 去重
        rolesPer = rolesPer.stream().filter(distinctByKey(b -> b.getPermissionId())).collect(Collectors.toList());

        for (SysPermission sysPermission: sysPermissions) {
                RoleHavePermissionResp roleHavePermissionResp = new RoleHavePermissionResp();
                if (sysPermission.getPid() == 0) {
                    for(SysRolePermission gp:rolesPer){
                        if(gp.getPermissionId().equals(sysPermission.getId())){
                            CopyUtils.copyProperties(sysPermission, roleHavePermissionResp);
                            roleHavePermissionResp.setChildren(getChildrenListByRoleIds(sysPermission.getId(), sysPermissions,rolesPer));
                            respList.add(roleHavePermissionResp);
                        }
                    }
                }
            }

            return respList;

    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    public List<RoleHavePermissionResp> getChildrenListByRoleIds(Long pid, List<SysPermission> list,List<SysRolePermission> rolesPer){
            List<RoleHavePermissionResp> respList = new ArrayList<>();
            for (SysPermission sysPermission: list) {
                RoleHavePermissionResp roleHavePermissionResp = new RoleHavePermissionResp();
                if (sysPermission.getPid().equals(pid)) {
                    for(SysRolePermission gp:rolesPer){
                        if(gp.getPermissionId().equals(sysPermission.getId())){
                            CopyUtils.copyProperties(sysPermission, roleHavePermissionResp);
                            roleHavePermissionResp.setChildren(getChildrenListByRoleIds(sysPermission.getId(), list,rolesPer));
                            respList.add(roleHavePermissionResp);
                        }
                    }

                }
            }
            return respList;
    }
}
