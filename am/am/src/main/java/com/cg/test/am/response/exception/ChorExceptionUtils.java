package com.cg.test.am.response.exception;

import com.cg.test.am.response.core.ChorResponse;
import com.cg.test.am.response.core.ErrorCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理
 * @author hao.yan
 */
@Slf4j
public class ChorExceptionUtils {

    /**
     * 静态解析异常。可以直接调用
     * @param e 异常
     * @return response
     */
    public static ChorResponse<Void> resolveException(Throwable e) {
        log.error("", e);
        String message = e.getMessage();
        String className = e.getClass().getName();
        ErrorCode errorCode;
        if (className.contains("UsernameNotFoundException")) {
            errorCode = ErrorCode.USERNAME_NOT_FOUND;
        } else if (className.contains("BadCredentialsException")) {
            errorCode = ErrorCode.BAD_CREDENTIALS;
        } else if (className.contains("AccountExpiredException")) {
            errorCode = ErrorCode.ACCOUNT_EXPIRED;
        } else if (className.contains("LockedException")) {
            errorCode = ErrorCode.ACCOUNT_LOCKED;
        } else if (className.contains("DisabledException")) {
            errorCode = ErrorCode.ACCOUNT_DISABLED;
        } else if (className.contains("CredentialsExpiredException")) {
            errorCode = ErrorCode.CREDENTIALS_EXPIRED;
        } else if (className.contains("InvalidClientException")) {
            errorCode = ErrorCode.INVALID_CLIENT;
        } else if (className.contains("UnauthorizedClientException")) {
            errorCode = ErrorCode.UNAUTHORIZED_CLIENT;
        }else if (className.contains("InsufficientAuthenticationException") || className.contains("AuthenticationCredentialsNotFoundException")) {
            errorCode = ErrorCode.UNAUTHORIZED;
        } else if (className.contains("InvalidGrantException")) {
            errorCode = ErrorCode.INVALID_GRANT;
            if ("Bad credentials".contains(message)) {
                errorCode = ErrorCode.BAD_CREDENTIALS;
            } else if ("User is disabled".contains(message)) {
                errorCode = ErrorCode.ACCOUNT_DISABLED;
            } else if ("User account is locked".contains(message)) {
                errorCode = ErrorCode.ACCOUNT_LOCKED;
            }
        } else if (className.contains("InvalidScopeException")) {
            errorCode = ErrorCode.INVALID_SCOPE;
        } else if (className.contains("InvalidTokenException")) {
            errorCode = ErrorCode.INVALID_TOKEN;
        } else if (className.contains("InvalidRequestException")) {
            errorCode = ErrorCode.INVALID_REQUEST;
        } else if (className.contains("RedirectMismatchException")) {
            errorCode = ErrorCode.REDIRECT_URI_MISMATCH;
        } else if (className.contains("UnsupportedGrantTypeException")) {
            errorCode = ErrorCode.UNSUPPORTED_GRANT_TYPE;
        } else if (className.contains("UnsupportedResponseTypeException")) {
            errorCode = ErrorCode.UNSUPPORTED_RESPONSE_TYPE;
        } else if (className.contains("UserDeniedAuthorizationException")) {
            errorCode = ErrorCode.ACCESS_DENIED;
        } else if (className.contains("AccessDeniedException")) {
            errorCode = ErrorCode.ACCESS_DENIED;
        } else if(className.contains("MissingServletRequestParameterException")) {
            errorCode = ErrorCode.PARAMS_ERROR;
        } else if(className.contains("ValidateException")) {
            errorCode = ErrorCode.PARAMS_ERROR;
        } else if(className.contains("BusinessException")) {
            errorCode = ErrorCode.PARAMS_ERROR;
        } else {
            errorCode = ErrorCode.SYSTEM_ERROR;
        }

        return new ChorResponse<>(errorCode);
    }

}
