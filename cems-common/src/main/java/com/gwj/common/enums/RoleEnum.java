package com.gwj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {

    USER("用户", 1),
    AUDITOR("审核员", 2),
    ADMIN("管理员", 3);


    private final String desc;

    private final Integer code;

    public static boolean validRole(Integer code) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
