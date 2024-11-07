package com.cg.test.am.error;

import com.cg.test.am.response.core.ChorCode;
import lombok.Getter;

public enum AmErrorCode implements ChorCode {

    NULL_FOUND("300001", "找不到对象"),

    ASSET_TYPE_NULL_FOUND("300002", "资产类型不存在"),

    ASSET_TYPE_RELATIONSHIP_ERROR("300003", "关系错误"),

    OUT_LIMIT("400001", "超出限制"),

    NUM_ABNORMAL("400002", "该资产类型数量不能为多个"),

    LOG_EXPIRED("400003", "登录身份已失效，请重新登录"),

    USERNAME_NOTFOUND("400004", "用户名不存在"),

    PASSWORD_ERROR("400005",  "密码错误"),

    ROLE_ERROR("400006",  "未配置角色"),


    AUTHORIZATION_ERROR("400007",  "授权失败"),

    ASSET_NOT_BELONG("400008",  "资产不属于用户部门，无权操作"),

    LOGIN_ERROR("400009",  "用户名或者密码错误"),

    SERVER_ERROR("500001", "系统繁忙，请稍后重试"),

    SOURCE_EXIST("600001", "对象已经存在"),

    STATUS_NO_CHANGE("600002", "此状态数据不能改变"),

    HAVE_INVENTORY("600003", "请勿重复盘点"),

    BE_LOCKED("600004", "资产已经被锁定，无法领用"),

    ASSET_IN_CHECK("600005", "资产盘点中，无法进行此操作"),

    ASSET_CHECK_LOCK_OFF("600006", "无法进行资产盘点，请打开资产盘点开关"),

    NOT_ENOUGH("700001", "数量不足"),

    NO_INSERT_CHARGE_OFF("700002", "此产品已提交核销记录,请勿重复提交"),

    NO_AUTHORIZATION("700003", "权限不足"),

    ASSET_TYPE_SCRAP_ERROR("700004", "低值易耗品不可核销"),

    ASSET_TYPE_PRINT_ERROR("700005", "低值易耗品不可打印资产标签"),

    AMOUNT_ERROR("700006", "金额输入错误"),

    NUM_ERROR("700007", "数量填写不正确"),

    ASSET_TYPE_RETURN_ERROR("700008", "低值易耗品无需归还");


    AmErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    @Getter
    private String code;

    @Getter
    private String message;
}
