package com.gwj.cems.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gwj.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 赛事
 *
 * @author 
 * @since 2024-02-27
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("CAMPUS_EVENT_MANAGEMENT.EVENT")
public class Event extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 赛事名称
     */
    @TableField("EVENT_NAME")
    private String eventName;

    /**
     * 举办场地
     */
    @TableField("VENUE")
    private String venue;

    /**
     * 赛事年度
     */
    @TableField("YEAR")
    private Integer year;

    /**
     * 报名开始时间
     */
    @TableField("REGISTER_START")
    private Date registerStart;

    /**
     * 报名结束时间
     */
    @TableField("REGISTER_END")
    private Date registerEnd;

    /**
     * 比赛开始时间
     */
    @TableField("EVENT_START")
    private Date eventStart;

    /**
     * 比赛结束时间
     */
    @TableField("EVENT_END")
    private Date eventEnd;

    /**
     * 最大兼项人数
     */
    @TableField("MAX_CONCURRENT")
    private Integer maxConcurrent;

    /**
     * 状态
     */
    @TableField("STATE")
    private Integer state;
}
