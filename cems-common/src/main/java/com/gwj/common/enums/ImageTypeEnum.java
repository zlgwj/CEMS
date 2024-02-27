package com.gwj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ImageTypeEnum {
    UAV(2, "uav"),
    TVDI(1, "drought")
    ;
    private Integer type;
    private String name;
}
