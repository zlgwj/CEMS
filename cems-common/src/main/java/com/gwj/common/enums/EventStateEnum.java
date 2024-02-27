package com.gwj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventStateEnum {

    APPROVAL(0, "已立项"),
    SIGN_UP(1, "报名中"),
    SIGN_UP_END(2, "报名结束"),
    EVENT_START(3, "活动开始"),
    EVENT_END(4, "活动结束"),
    EVENT_CANCEL(5, "活动取消");
    private final Integer code;
    private final String msg;

}
