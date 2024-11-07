package com.cg.test.am.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cg.test.am.error.AmErrorCode;
import com.cg.test.am.mapper.SysPostMapper;
import com.cg.test.am.mapper.SysUserMapper;
import com.cg.test.am.model.SysUser;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.service.SysPostService;
import com.cg.test.am.utils.CopyUtils;
import com.cg.test.am.model.SysPost;
import com.cg.test.am.vo.ParamsConstant;
import com.cg.test.am.vo.response.SysTree;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysPostServiceImpl implements SysPostService {

    @Resource
    SysPostMapper sysPostMapper;
    @Resource
    SysUserMapper sysUserMapper;
    
    @Override
    public List<SysTree> treeList(Long userId){
        try{
            List<SysPost> sysPost = sysPostMapper.selectList(new QueryWrapper<SysPost>()
                    .eq("del_flag", ParamsConstant.DEL_FLAG_DEFAULT));
            List<SysTree> respList = new ArrayList<>();

            // 用户Id不等于0,查询该用户拥有的id,设置树结构默认选中
            if(!userId.equals(ParamsConstant.DEL_FLAG_DEFAULT_VALUE)){

                //根据用户id查询岗位
                SysUser sysUserInfo = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", userId));

                for (SysPost sp: sysPost) {
                    SysTree sysTree = new SysTree();
                    if (sp.getPid() == 0) {
                        CopyUtils.copyProperties(sp, sysTree);
                        sysTree.setId(sp.getId().longValue());
                        sysTree.setPid(sp.getPid().longValue());
                        if(sp.getId().toString().equals(sysUserInfo.getPostId())){
                            sysTree.setSelect(ParamsConstant.SELECT);
                        }
                        sysTree.setList(getChildrenList(sp.getId(), sysPost,Integer.parseInt(sysUserInfo.getPostId())));
                        respList.add(sysTree);
                    }
                }
            }else{
                for (SysPost sp: sysPost) {
                    SysTree sysTree = new SysTree();
                    if (sp.getPid() == 0) {
                        CopyUtils.copyProperties(sp, sysTree);
                        sysTree.setId(sp.getId().longValue());
                        sysTree.setPid(sp.getPid().longValue());
                        sysTree.setList(getChildrenList(sp.getId(), sysPost,0));
                        respList.add(sysTree);
                    }
                }
            }
            return respList;
        }catch (Exception e){
            throw new ChorBizException(AmErrorCode.SERVER_ERROR);
        }
    }

    public List<SysTree> getChildrenList(Integer pid, List<SysPost> list,Integer postId) {

        List<SysTree> respList = new ArrayList<>();
        for (SysPost sp: list) {
            SysTree sysTree = new SysTree();
            if (sp.getPid().equals(pid) ) {
                if(sp.getId().equals(postId)&&postId!=0){
                    sysTree.setSelect(ParamsConstant.SELECT);
                }
                CopyUtils.copyProperties(sp, sysTree);
                sysTree.setId(sp.getId().longValue());
                sysTree.setPid(sp.getPid().longValue());
                sysTree.setList(getChildrenList(sp.getId(), list,postId));
                respList.add(sysTree);
            }
        }
        return respList;
    }
}
