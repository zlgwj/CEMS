package com.gwj.cems.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gwj.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 比赛项目
 *
 * @author 
 * @since 2024-02-27
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("CAMPUS_EVENT_MANAGEMENT.PROGRAM")
public class Program extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 赛事ID
     */
    @TableField("EVENT_GUID")
    private String eventGuid;

    /**
     * 项目名称
     */
    @TableField("PROGRAM_NAME")
    private String programName;

    /**
     * 参赛者性别限制
     */
    @TableField("GENDER_LIMIT")
    private Integer genderLimit;

    /**
     * 赛赛者人数限制
     */
    @TableField("ENTRANTS_LIMIT")
    private Integer entrantsLimit;

    /**
     * 裁判ID
     */
    @TableField("UNPIRE_GUID")
    private String unpireGuid;

    /**
     * 最大用时;按两个小时为一个单位计算
     */
    @TableField("MAX_TIME")
    private Integer maxTime;

    /**
     * 项目类型
     */
    @TableField("PROGRAM_TYPE")
    private Integer programType;

    /**
     * 比赛日期
     */
    @TableField("PLAY_TIME")
    private Date playTime;

    /**
     * 比赛顺序
     */
    @TableField("PLAY_SORT")
    private Integer playSort;

    /**
     * 比赛顺序
     */
    @TableField("AM_PM")
    private Integer amPm;

    /**
     * 创建人
     */
    @TableField("CREATED_BY")
    private String createdBy;
}
