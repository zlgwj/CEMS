package com.gwj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {

    LINK(1, "链接消息"),
    TEXT(2, "文本消息");

    private Integer code;
    private String msg;

}
