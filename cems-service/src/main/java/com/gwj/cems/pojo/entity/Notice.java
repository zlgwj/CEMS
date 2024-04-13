package com.gwj.cems.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gwj.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 公告
 * </p>
 *
 * @author
 * @since 2024-03-28
 */
@Getter
@Setter
@TableName("CAMPUS_EVENT_MANAGEMENT.NOTICE")
public class Notice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 描述信息
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 链接
     */
    @TableField("LINK")
    private String link;

    /**
     * 消息正文
     */
    @TableField("CONTENT")
    private String content;

    /**
     * 通知类型;1：文本通知 2：链接通知
     */
    @TableField("NOTICE_TYPE")
    private Integer noticeType;
}
