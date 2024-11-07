package com.cg.test.am.response.utils;

import com.cg.test.am.response.core.ChorCode;
import com.cg.test.am.response.core.ChorPageResponse;
import com.github.pagehelper.Page;
import org.springframework.util.CollectionUtils;

import java.util.Collection;


/**
 * 返回工具类
 * @author hao.yan
 */
public class ChorPageResponseUtils {

    public static ChorPageResponse<Void> success(){
        return success(null);
    }


    public static <T> ChorPageResponse<T> success(Collection<T> datas){
        if (CollectionUtils.isEmpty(datas)){
            return success(1, 0, 0L, datas);
        }
        if (datas instanceof Page){
            Page<T> page  = (Page<T>) datas;
            return success(page.getPageNum(), page.getPageSize(), page.getTotal(), page.getResult());
        }
        return success(1, datas.size(), Integer.valueOf(datas.size()).longValue(), datas);
    }

    public static <T> ChorPageResponse<T> success(Integer pageSize, Integer pageNo, Long total, Collection<T> datas){
        return new ChorPageResponse<>(pageNo, pageSize, total, datas);
    }

    public static ChorPageResponse error(ChorCode chorCode){
        return error(chorCode.getCode(), chorCode.getMessage());
    }

    public static ChorPageResponse error(String code, String message){
        return new ChorPageResponse(code, message);
    }

}
