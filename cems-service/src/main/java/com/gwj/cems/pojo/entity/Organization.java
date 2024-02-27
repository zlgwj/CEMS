package com.gwj.cems.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gwj.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织
 *
 * @author
 * @since 2024-02-27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("CAMPUS_EVENT_MANAGEMENT.ORGANIZATION")
public class Organization extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 专业
     */
    @TableField("PROFESSION")
    private String profession;

    /**
     * 年级
     */
    @TableField("GRADE")
    private String grade;

    /**
     * 班级
     */
    @TableField("KLASS")
    private String klass;
}
