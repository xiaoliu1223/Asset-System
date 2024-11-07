package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.SysRolePermissionMapper;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.vo.response.SysRoleResp;
import com.cg.test.am.vo.response.GeneralResp;
import com.cg.test.am.mapper.SysRoleMapper;
import com.cg.test.am.model.SysRole;
import com.cg.test.am.model.SysRolePermission;
import com.cg.test.am.service.SysRoleService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.SysRoleListReq;
import com.cg.test.am.vo.request.SysRoleReq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    SysRoleMapper sysRoleMapper;

    @Resource
    SysRolePermissionMapper sysRolePermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void  save(SysRole sysRole){

        try {

            int count = sysRoleMapper.selectCount(new QueryWrapper<SysRole>()
                    .eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).eq("name", sysRole.getName()));
            if (count > 0) {
                throw new ChorBizException(AmErrorCode.SOURCE_EXIST);
            }

            sysRole.setCreateTime(System.currentTimeMillis());
            sysRole.setDelFlag(ParamsConstant.DEL_FLAG_DEFAULT);
            sysRoleMapper.insertSelective(sysRole);
            if (!CollectionUtils.isEmpty(sysRole.getPermissionIds())) {
                saveRolePermission(sysRole.getId(), sysRole.getPermissionIds());
            }

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    /**
     * 写入权限和角色关联关系
     * @param roleId
     * @param permissionIds
     */
    private void saveRolePermission(Long roleId, List<Long> permissionIds) {
        sysRoleMapper.deleteRolePermission(roleId);
        if (!CollectionUtils.isEmpty(permissionIds)) {
            sysRoleMapper.saveRolePermission(roleId, permissionIds);
        }
    }

    @Override
    public Map<String, Object> list(SysRoleListReq req) {

        try {
            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysRoleMapper.count(req);
            List<SysRole> list = sysRoleMapper.list(req, offset, limit);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total_record", count);
            map.put("data", list);
            return map;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }

    }

    @Override
    public SysRoleResp find(Long id) {

        try {
            SysRole info = sysRoleMapper.selectByPrimaryKey(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            SysRoleResp sysRoleResp = new SysRoleResp();
            CopyUtils.copyProperties(info, sysRoleResp);
            return sysRoleResp;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(SysRoleReq req, Long id) {

        try {

            SysRole info = sysRoleMapper.selectByPrimaryKey(id);

            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            CopyUtils.copyProperties(req, info);
            long updateTime = System.currentTimeMillis();
            info.setUpdateTime(updateTime);
            sysRoleMapper.updateByPrimaryKey(info);
            if (!CollectionUtils.isEmpty(req.getPermissionIds())) {
                saveRolePermission(id, req.getPermissionIds());
            }

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

            SysRole info = sysRoleMapper.selectByPrimaryKey(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            info.setDelFlag(ParamsConstant.DEL_FLAG_DELETED);
            info.setUpdateTime(System.currentTimeMillis());
            sysRoleMapper.updateById(info);
            sysRolePermissionMapper.delete(new QueryWrapper<SysRolePermission>().eq("role_id", id));

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public  List<GeneralResp> allList(){
        return sysRoleMapper.selectAllList();
    }

    @Override
    public List<GeneralResp> listByRoleId(Long roleId){
        return sysRoleMapper.listByRoleId(roleId);
    }

    @Override
    public  List<GeneralResp> listByUserId(Long userId){
        return sysRoleMapper.listByUserId(userId);
    }

}
