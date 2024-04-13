package com.gwj.cems.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gwj.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户通知
 * </p>
 *
 * @author
 * @since 2024-03-28
 */
@Getter
@Setter
@TableName("CAMPUS_EVENT_MANAGEMENT.USER_NOTICE")
public class UserNotice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户GUID
     */
    @TableField("USER_GUID")
    private String userGuid;

    /**
     * 消息GUID
     */
    @TableField("NOTICE_GUID")
    private String noticeGuid;
}
