package com.cg.test.am.response.utils;


import com.cg.test.am.response.core.ChorCode;
import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.exception.ChorBizException;

/**
 * 返回工具类
 * @author hao.yan
 */
public class ChorResponseUtils {

    public static ChorResponse<Void> success(){
        return success(null);
    }

    public static <T> ChorResponse<T> success(T o){
        return new ChorResponse<>(o);
    }

    public static <T> ChorResponse<T> error(ChorCode chorCode){
        return error(chorCode.getCode(), chorCode.getMessage());
    }

    public static <T> ChorResponse<T> error(String code, String message){
        return error(code, message, null);
    }

    public static <T> ChorResponse<T> error(String code, String message, T o){
        return new ChorResponse<>(code, message, o);
    }

    /**
     * 处理统一返回结果
     * @param response 返回结果
     * @param code 默认错误代码
     * @param <T> 返回的数据
     */
    public static <T> T getResponse(ChorResponse<T> response, ChorCode code){
        if (response == null){
            throw new ChorBizException(code);
        }

        if (!response.getCode().equals(ChorCode.SUCCESS_CODE)){
            throw new ChorBizException(response.getCode(), response.getMessage());
        }

        return response.getData();
    }

}
