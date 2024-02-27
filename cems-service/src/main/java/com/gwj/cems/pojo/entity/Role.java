package com.gwj.cems.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gwj.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author 
 * @since 2024-02-27
 */
@Getter
@Setter
@TableName("CAMPUS_EVENT_MANAGEMENT.ROLE")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @TableField("ROLE_NAME")
    private String roleName;
}
