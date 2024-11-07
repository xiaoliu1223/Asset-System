package com.cg.test.am.service;

import com.cg.test.am.model.SysAssetType;
import com.cg.test.am.vo.request.SysAssetTypeListReq;
import com.cg.test.am.vo.request.SysAssetTypeReq;
import com.cg.test.am.vo.response.SysAssetTypeResp;
import com.cg.test.am.vo.response.SysTree;

import java.util.List;
import java.util.Map;

public interface SysAssetTypeService {

    /**
     * 创建资产类别
     * @param sysAssetType
     */
    void save(SysAssetType sysAssetType);

    /**
     * 资产类别列表（完整信息）
     * @param req
     */
    Map<String, Object> list(SysAssetTypeListReq req);

    /**
     * 取得资产类别详情
     * @param id
     * @return
     */
    SysAssetTypeResp find(Long id);

    /**
     * 修改资产类别信息
     * @param req
     * @param id
     */
    void modify(SysAssetTypeReq req, Long id);

    /**
     * 删除
     * @param id
     */
    void remove(Long id);

    /**
     * 获取树状图
     */
    List<SysTree> treeList();
}
