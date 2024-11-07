package com.cg.test.am.response.configuration;


import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.core.ErrorCode;
import com.cg.test.am.response.exception.ChorBizException;
import com.cg.test.am.response.exception.ChorExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 * @author hao.yan
 */
@Slf4j
@RestControllerAdvice
public class ChorControllerAdvice {

    /**
     * 业务异常
     * @param exp 异常
     * @return response
     */
    @ExceptionHandler(Exception.class)
    public ChorResponse<Void> bizException(Exception exp) {
        log.error("系统异常:", exp);
        return ChorExceptionUtils.resolveException(exp);
    }

    @ExceptionHandler(ApplicationException.class)
    public ChorResponse<Void> applicationException(ApplicationException ex) {
        log.error("业务异常:", ex);
        return ChorExceptionUtils.resolveException(ex);
    }

    /**
     * 业务异常
     * @param exp 异常
     * @return response
     */
    @ExceptionHandler(ChorBizException.class)
    public ChorResponse<Void> bizException(ChorBizException exp) {
        log.error("业务异常:", exp);
        return new ChorResponse<>(exp.getCode(), exp.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ChorResponse<Void> handleBindException(BindException exp) {
        FieldError error = exp.getFieldError();
        if (error == null){
            return new ChorResponse<>(ErrorCode.PARAMS_ERROR);
        }
        String message = error.getDefaultMessage();
        if (StringUtils.isEmpty(message)){
            return new ChorResponse<>(ErrorCode.PARAMS_ERROR);
        }

        String[] errorCode = message.split("-");
        if (errorCode.length == 1){
            return new ChorResponse<>(ErrorCode.PARAMS_ERROR.getCode(), errorCode[0]);
        }

        return new ChorResponse<>(errorCode[0], errorCode[1]);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ChorResponse<Void> processMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((s1, s2) -> s1.concat(",").concat(s2)).get();
        return new ChorResponse<>(ErrorCode.PARAMS_ERROR.getCode(), errorMessage);
    }

}
