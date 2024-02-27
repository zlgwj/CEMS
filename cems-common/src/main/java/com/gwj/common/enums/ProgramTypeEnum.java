package com.gwj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProgramTypeEnum {

    FIELD_EVENT(1, "田赛"),
    TRACK_EVENT(2, "径赛"),
    MASS_EVENT(3, "大众项目");

    private final Integer code;
    private final String msg;

}
