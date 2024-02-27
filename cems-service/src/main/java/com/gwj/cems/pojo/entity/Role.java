package com.gwj.cems.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gwj.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色管理
 *
 * @author 
 * @since 2024-02-27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("CAMPUS_EVENT_MANAGEMENT.ROLE")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @TableField("ROLE_NAME")
    private String roleName;
}
