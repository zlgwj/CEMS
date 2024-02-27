package com.gwj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonEnum {

    YES(1, "是"),
    NO(0, "否");
    private final Integer code;
    private final String msg;
}
