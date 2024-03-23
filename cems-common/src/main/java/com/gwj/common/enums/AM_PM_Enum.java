package com.gwj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AM_PM_Enum {

    AM(1, "上午"),
    PM(2, "下午");

    private final Integer code;
    private final String msg;
}
