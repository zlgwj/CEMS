package com.gwj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeleteEnum {

    DELETE(1, "已删除"),
    NOT_DELETE(0, "未删除");

    private final Integer code;
    private final String msg;
}
