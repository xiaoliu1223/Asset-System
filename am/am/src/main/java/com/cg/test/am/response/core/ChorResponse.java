package com.cg.test.am.response.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 统一返回数据的格式
 * @author hao.yan
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ChorResponse<T> extends Result {

    private static final long serialVersionUID = 6954326175128128320L;

    private T data;

    public ChorResponse(T data){
        this(ErrorCode.SUCCESS, data);
    }

    public ChorResponse(ErrorCode errorCode){
        this(errorCode.getCode(), errorCode.getMessage());
    }

    public ChorResponse(String code, String message){
        this(code, message, null);
    }

    public ChorResponse(ErrorCode errorCode, T data){
        this(errorCode.getCode(), errorCode.getMessage(), data);
    }

    public ChorResponse(String code, String message, T data){
        super(code, message);
        this.data = data;
    }


}
