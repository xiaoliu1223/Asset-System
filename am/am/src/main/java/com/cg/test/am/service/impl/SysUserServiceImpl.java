package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.*;
import com.cg.test.am.model.SysDepartment;
import com.cg.test.am.model.SysLoginLog;
import com.cg.test.am.model.SysPost;
import com.cg.test.am.model.SysUser;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysPermissionService;
import com.cg.test.am.service.SysRoleService;
import com.cg.test.am.service.SysUserService;
import com.cg.test.am.utils.*;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.request.ChangePasswordReq;
import com.cg.test.am.vo.request.SysUserListReq;
import com.cg.test.am.vo.request.SysUserReq;
import com.cg.test.am.vo.request.SysUserUpdateReq;
import com.cg.test.am.vo.request.applet.AppletLoginReq;
import com.cg.test.am.vo.response.LoginResp;
import com.cg.test.am.vo.response.RoleHavePermissionResp;
import com.cg.test.am.vo.response.SysUserResp;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysLoginLogMapper sysLoginLogMapper;

    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    @Resource
    SysRoleService sysRoleService;

    @Resource
    SysPermissionService sysPermissionServiceImpl;

    @Resource
    SysDepartmentMapper sysDepartmentMapper;

    @Resource
    HttpServletRequest request;

    @Resource
    SysPostMapper sysPostMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginResp apiLogin(SysUserReq user) {

        try {

            // 判断用户名密码
//            SysUser userInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("tel", user.getUsername()));
            SysUser userInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                    .eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).and(query-> query.eq("username", user.getCertificate()).or().eq("tel", user.getCertificate())));

            if (null == userInfo) {
                throw new ChorBizException(AmErrorCode.LOGIN_ERROR);
            }

            String loginPassword = MD5Util.encode(user.getPassword() + userInfo.getSalt(), "UTF-8");
            if (!loginPassword.equals(userInfo.getPassword())) {
                throw new ChorBizException(AmErrorCode.LOGIN_ERROR);
            }

            //登录日志
            SysLoginLog record = new SysLoginLog();
            record.setIpAddress(IpUtils.getIpAddr(request));
            record.setUserId(userInfo.getId().longValue());
            record.setLoginTime(System.currentTimeMillis());
            sysLoginLogMapper.insertSelective(record);

            LoginResp res = new LoginResp();
            SysDepartment sysDepartment = sysDepartmentMapper.selectById(userInfo.getDepartmentId());
            if (null != sysDepartment) {
                res.setDepartmentName(sysDepartment.getName());
            }

            res.setDepartmentId(userInfo.getDepartmentId().intValue());

            // 角色
            res.setRoleIds(sysRoleService.listByUserId(userInfo.getId().longValue()));
            if(res.getRoleIds().size() <= 0){
                throw new ChorBizException(AmErrorCode.ROLE_ERROR);
            }
            List<Long> roleIds = new ArrayList<>();
            for (int j = 0;j<res.getRoleIds().size();j++){
                roleIds.add(res.getRoleIds().get(j).getId());
            }

            //根据角色查询权限
            List<RoleHavePermissionResp> resp = sysPermissionServiceImpl.getTreeListByRoleId(roleIds);
            res.setPermission(resp);

            //根据自己所属部门查询下级所有部门
            List<String> resList = new ArrayList<>();
            List<SysDepartment> list = sysDepartmentMapper.selectList(new QueryWrapper<SysDepartment>().eq("del_flag",ParamsConstant.DEL_FLAG_DEFAULT));
            resList = DepartmentUtils.getChildList(userInfo.getDepartmentId(), list, resList);
            resList.add(userInfo.getDepartmentId() + "");
            String departmentIds = String.join(",", resList);
            res.setDepartmentIds(departmentIds);
            CopyUtils.copyProperties(userInfo, res);

            //生成token
            String token = JwtUtil.createJwt(8640000L, userInfo, departmentIds);
            res.setToken(token);
            return res;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public void save(SysUser sysUser) {
        try {

            int count = sysUserMapper.selectCount(new QueryWrapper<SysUser>().eq("tel", sysUser.getTel()).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            if (count > 0) {
                throw new ChorBizException(AmErrorCode.SOURCE_EXIST);
            }

            sysUser.setSalt(DigestUtils
                    .md5Hex(UUID.randomUUID().toString() + System.currentTimeMillis() + UUID.randomUUID().toString()));
            sysUser.setPassword(MD5Util.encode(sysUser.getTel().substring(5) + sysUser.getSalt(), "UTF-8"));
            sysUser.setCreateTime(System.currentTimeMillis());
            sysUser.setDelFlag(ParamsConstant.DEL_FLAG_DEFAULT);

            // 取得上级
            SysPost sysPost = sysPostMapper.selectById(sysUser.getPostId());
            if (null != sysPost) {
                sysUser.setSuperiorPostId(sysPost.getPid().toString());
            }
            sysUserMapper.insert(sysUser);

            //添加对应的角色
            saveUserRoles(sysUser.getId(), sysUser.getRoleIds());

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    private void saveUserRoles(Integer userId, List<Long> roleIds) {
        if (roleIds != null) {
            sysUserRoleMapper.deleteUserRole(userId);
            if (!CollectionUtils.isEmpty(roleIds)) {
                sysUserRoleMapper.saveUserRoles(userId, roleIds);
            }
        }
    }

    @Override
    public Map<String, Object> list(SysUserListReq req) {
        try {
            Integer limit = req.getLimit();
            Integer offset = (req.getCurrent() - 1) * limit;
            int count = sysUserMapper.count(req);
            List<SysUserResp> list = sysUserMapper.list(req, offset, limit);

            list = list.stream().map(sysUserResp -> {
                List<Long> roleIdList = sysUserRoleMapper.getRoleIdList(sysUserResp.getId());
                sysUserResp.setRoleIds(roleIdList);
                return sysUserResp;
            }).collect(Collectors.toList());
            Map<String, Object> map = new HashMap<>();
            map.put("total_record", count);
            map.put("data", list);
            return map;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public SysUserResp find(Integer id) {

        try {

            SysUser info = sysUserMapper.selectByPrimaryKey(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            SysUserResp sysUserResp = new SysUserResp();
            CopyUtils.copyProperties(info, sysUserResp);
            return sysUserResp;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(SysUserUpdateReq req, Integer id) {

        try {

            SysUser info = sysUserMapper.selectByPrimaryKey(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            CopyUtils.copyProperties(req, info);
            long updateTime = System.currentTimeMillis();
            info.setUpdateTime(updateTime);
            sysUserMapper.updateByPrimaryKey(info);
            saveUserRoles(id, req.getRoleIds());
        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {

            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(Integer id) {
        try {

            SysUser info = sysUserMapper.selectByPrimaryKey(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            SysUser res = new SysUser();
            res.setId(id);
            res.setDelFlag(ParamsConstant.DEL_FLAG_DELETED);
            sysUserMapper.updateById(res);

            // 删除用户角色关联信息
            sysUserRoleMapper.deleteUserRole(id);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void initial(Integer id) {
        try {

            SysUser info = sysUserMapper.selectByPrimaryKey(id);
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }
            SysUser res = new SysUser();
            res.setId(id);
            res.setPassword(MD5Util.encode("88888888" + info.getSalt(), "UTF-8"));
            sysUserMapper.updateById(res);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changePassword(ChangePasswordReq req) {
        try {

            SysUser info = sysUserMapper.selectByPrimaryKey(req.getId());
            if (null == info) {
                throw new ChorBizException(AmErrorCode.NULL_FOUND);
            }

            String old = MD5Util.encode(req.getOldPassword() + info.getSalt(), "UTF-8");
            if (!old.equals(info.getPassword())) {
                throw new ChorBizException(AmErrorCode.PASSWORD_ERROR);
            }
            SysUser res = new SysUser();
            res.setId(req.getId());
            res.setPassword(MD5Util.encode(req.getNewPassword() + info.getSalt(), "UTF-8"));
            sysUserMapper.updateById(res);

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginResp appletLogin(AppletLoginReq req) {

        try {

//            SysUser userInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
//                    .eq("tel", req.getTel()).eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            SysUser userInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                    .eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT).and(query-> query.eq("username", req.getTel()).or().eq("tel", req.getTel())));

            if (null == userInfo) {
                throw new ChorBizException(AmErrorCode.LOGIN_ERROR);
            }

            String password = MD5Util.encode(req.getPassword() + userInfo.getSalt(), "UTF-8");
            if (!password.equals(userInfo.getPassword())) {
                throw new ChorBizException(AmErrorCode.LOGIN_ERROR);
            }

            if (null == userInfo.getOpenid()) {
                String jsCode = req.getJsCode();
                String openid = AppletUtils.getOpenid(jsCode);
                userInfo.setOpenid(openid);
                sysUserMapper.updateById(userInfo);
            }

            //登录日志
            SysLoginLog record = new SysLoginLog();
            record.setIpAddress(IpUtils.getIpAddr(request));
            record.setUserId(userInfo.getId().longValue());
            record.setLoginTime(System.currentTimeMillis());
            sysLoginLogMapper.insertSelective(record);

            LoginResp res = new LoginResp();
            SysDepartment sysDepartment = sysDepartmentMapper.selectById(userInfo.getDepartmentId());
            if (null != sysDepartment) {
                res.setDepartmentName(sysDepartment.getName());
            }

            res.setDepartmentId(userInfo.getDepartmentId().intValue());

            //角色
            res.setRoleIds(sysRoleService.listByUserId(userInfo.getId().longValue()));
            if(res.getRoleIds().size() <= 0){
                throw new ChorBizException(AmErrorCode.ROLE_ERROR);
            }
            List<Long> roleIds = new ArrayList<>();
            for (int j = 0;j<res.getRoleIds().size();j++){
                roleIds.add(res.getRoleIds().get(j).getId());
            }

            //根据角色查询权限
            List<RoleHavePermissionResp> resp = sysPermissionServiceImpl.getTreeListByRoleId(roleIds);
            res.setPermission(resp);

            //根据自己所属部门查询下级所有部门
            List<String> resList = new ArrayList<>();
            List<SysDepartment> list = sysDepartmentMapper.selectList(new QueryWrapper<SysDepartment>().eq("del_flag",ParamsConstant.DEL_FLAG_DEFAULT));

            resList = DepartmentUtils.getChildList(userInfo.getDepartmentId(), list, resList);
            resList.add(userInfo.getDepartmentId() + "");
            String departmentIds = String.join(",", resList);
            res.setDepartmentIds(departmentIds);
            CopyUtils.copyProperties(userInfo, res);

            //生成token
            String token = JwtUtil.createJwt(0L, userInfo, departmentIds);
            res.setToken(token);
            return res;

        } catch (ChorBizException e) {
            throw e;
        } catch (Exception e) {
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }
}
