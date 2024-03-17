package com.gwj.cems.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gwj.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户
 *
 * @author 
 * @since 2024-02-27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("CAMPUS_EVENT_MANAGEMENT.USER")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 学工号
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 性别
     */
    @TableField("GENDER")
    private Integer gender;

    /**
     * 手机号
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 角色
     */
    @TableField("ROLE")
    private Integer role;

    /**
     * 组织ID
     */
    @TableField("ORGANIZATION_GUID")
    private String organizationGuid;
}
