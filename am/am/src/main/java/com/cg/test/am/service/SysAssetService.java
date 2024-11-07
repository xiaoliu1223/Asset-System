package com.cg.test.am.service;

import com.cg.test.am.model.SysAsset;
import com.cg.test.am.vo.request.SysAssetCustomizedListReq;
import com.cg.test.am.vo.request.SysAssetListReq;
import com.cg.test.am.vo.response.SysAssetResp;
import com.cg.test.am.vo.request.SysAssetReq;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface SysAssetService {

    /**
     * 资产信息创建
     */
    void save(SysAsset sysAsset);

    /**
     * 资产信息列表
     */
    Map<String,Object> list(SysAssetListReq req);

    /**
     * 资产信息修改
     */
    void modify(SysAssetReq req, Long id);

    /**
     * 根据id查询详情
     */

    SysAssetResp find(Long id);

    /**
     * 资产信息导出
     */
    void  downloadExcel(HttpServletResponse response, String departmentId, String token);

    /**
     * 通过资产编号查看资产详情
     * @param assetCode
     * @return
     */
    SysAssetResp getAssetDetailByAssetCode(String assetCode);

    /**
     * 各部门资产信息
     * @param req
     * @return
     */
    Map<String,Object> getCustomizedList(SysAssetCustomizedListReq req);

}
