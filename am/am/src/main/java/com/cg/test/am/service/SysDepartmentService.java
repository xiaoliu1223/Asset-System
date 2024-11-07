package com.cg.test.am.service;

import com.cg.test.am.vo.request.SysDeparymentListReq;
import com.cg.test.am.vo.request.SysDeparymentReq;
import com.cg.test.am.vo.response.SysDeparymentResp;
import com.cg.test.am.model.SysDepartment;
import com.cg.test.am.vo.response.SysTree;

import java.util.List;
import java.util.Map;

public interface SysDepartmentService {

    /**
     * 创建部门
     * @param sysDepartment
     */
    void save(SysDepartment sysDepartment);

    /**
     * 部门列表（完整信息）
     * @param req
     */
    Map<String, Object> list(SysDeparymentListReq req);

    /**
     * 取得部门详情
     * @param id
     * @return
     */
    SysDeparymentResp find(Long id);

    /**
     * 修改部门信息
     * @param req
     * @param id
     */
    void modify(SysDeparymentReq req, Long id);

    /**
     * 删除
     * @param id
     */
    void remove(Long id);

    /**
     * 获取树状图
     */
    List<SysTree> treeList(Integer id);

    /**
     * 根据用户返回所有有权限的部门
     */
    List<SysDepartment> listByUser();
}
