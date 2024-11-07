package com.cg.test.am.service;

import com.cg.test.am.vo.response.SysTree;

import java.util.List;

public interface SysPostService {

    /**
     * 树形菜单
     */
    List<SysTree> treeList(Long userId);
}
