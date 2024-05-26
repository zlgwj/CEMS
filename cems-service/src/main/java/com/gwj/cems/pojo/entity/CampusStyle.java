package com.gwj.cems.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gwj.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 校园风采
 * </p>
 *
 * @author
 * @since 2024-04-19
 */
@Getter
@Setter
@TableName("CAMPUS_EVENT_MANAGEMENT.CAMPUS_STYLE")
public class CampusStyle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 图片地址
     */
    @TableField("URL")
    private String url;

    @TableField("NAME")
    private String name;

    /**
     * 存储路径
     */
    @TableField("PATH")
    private String path;

    /**
     * 排序
     */
    @TableField("SORT")
    private Integer sort;
}
