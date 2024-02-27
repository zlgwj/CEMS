package com.gwj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultEnum {

    SUCCESS(20000, "成功"),
    PARAM_ERROR(20400, "参数错误"),
    NOT_FOUND(20404, "未找到"),
    UNAUTHORIZED(20401, "未授权"),
    FORBIDDEN(20403, "认证失败"),
    NOT_IMPLEMENTED(20501, "未实现"),
    ERROR(20500, "服务异常"),
    LOGIN_ERROR(20405, "登录失败")
    ;


    private final Integer code;
    private final String msg;

}
