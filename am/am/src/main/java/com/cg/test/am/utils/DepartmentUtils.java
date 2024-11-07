package com.cg.test.am.utils;

import com.cg.test.am.model.SysDepartment;

import java.util.List;

/**
 * 部门数据处理工具类
 */
public class DepartmentUtils {

    /**
     * 获取下属部门
     * @param pid
     * @param list
     * @param respList
     * @return
     */
    public static List<String> getChildList(Long pid, List<SysDepartment> list, List<String> respList) {

        for (SysDepartment sysDepartment: list) {
            if (sysDepartment.getPid().equals(pid)) {
                respList.add(sysDepartment.getId() + "");
                getChildList(sysDepartment.getId(), list, respList);
            }
        }

        return respList;
    }

}
