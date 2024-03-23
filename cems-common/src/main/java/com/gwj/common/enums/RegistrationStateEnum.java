package com.gwj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegistrationStateEnum {

    SIGN_UP(1, "已报名"),
    FIRST_INSTANCED(2, "已初审"),
    FIRST_INSTANCED_REJECT(3, "初审驳回"),
    SIGN_UP_SUCCEED(4, "报名成功"),
    FINAL_REJECT(5, "终审退回"),
    SIGN_UP_CANCEL(0, "撤销报名");

    private final Integer code;

    private final String msg;

}
