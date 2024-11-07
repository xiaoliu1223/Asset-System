package com.cg.test.am.response.exception;

import com.cg.test.am.response.core.ChorCode;
import com.cg.test.am.response.core.ErrorCode;
import lombok.Getter;

/**
 * 业务异常
 * @author hao.yan
 */
public class ChorBizException extends RuntimeException {

    @Getter
    private String code;

    public ChorBizException() {
        this(ErrorCode.SYSTEM_ERROR);
    }

    public ChorBizException(String message) {
        super(message);
    }

    public ChorBizException(ChorCode chorCode) {
        this(chorCode.getCode(), chorCode.getMessage());
    }

    public ChorBizException(String code, String message) {
        super(message);
        this.code = code;
    }
}
