package com.cg.test.am.response.core;

import lombok.Getter;

/**
 * 错误代码
 * @author hao.yan
 */
public enum ErrorCode implements ChorCode {

    SUCCESS("000000", "SUCCESS"),

    /**
     * 为指定相应类型
     */
    UNSUPPORTED_RESPONSE_TYPE("200009", "为指定相应类型"),
    USERNAME_NOT_FOUND("200010", "用户名不存在"),
    BAD_CREDENTIALS("200011", "用户名密码错误"),
    ACCOUNT_EXPIRED("200012", "您的账户已过期"),
    ACCOUNT_LOCKED("200013", "您的账户已被锁定"),
    ACCOUNT_DISABLED("200014", "您的账户已被禁用"),
    CREDENTIALS_EXPIRED("200015", "您的证件已过期"),
    ACCESS_DENIED("200016", "无权访问"),
    ACCESS_COUNT("200017", "您的接口访问次数超越上限"),
    ACCESS_LIMIT("200018", "您的访问受限制"),
    INVALID_CLIENT("200100", "clientId无效"),
    UNAUTHORIZED_CLIENT("200101", "未授权"),
    UNAUTHORIZED("200102", "未授权"),
    INVALID_GRANT("200103", "grant_type无效"),
    INVALID_SCOPE("200104", "scope无效"),
    INVALID_TOKEN("200105", "访问令牌无效"),
    INVALID_REQUEST("200106", "无效请求"),
    REDIRECT_URI_MISMATCH("200107", "redirect_url无效"),
    UNSUPPORTED_GRANT_TYPE("200108", "grant_type无效"),
    PARAMS_ERROR("200109", "参数错误"),
    SYSTEM_ERROR("999999", "系统繁忙请稍后重试");

    @Getter
    private String code;

    @Getter
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
