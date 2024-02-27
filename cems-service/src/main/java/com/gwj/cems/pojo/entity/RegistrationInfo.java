package com.gwj.cems.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gwj.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 报名信息
 *
 * @author 
 * @since 2024-02-27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("CAMPUS_EVENT_MANAGEMENT.REGISTRATION_INFO")
@Accessors(chain = true)
public class RegistrationInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableField("USER_GUID")
    private String userGuid;

    @TableField("USER_ORGANIZATION")
    private String userOrganization;

    /**
     * 比赛项目ID
     */
    @TableField("PROGRAM_GUID")
    private String programGuid;

    /**
     * 报名状态
     */
    @TableField("STATE")
    private Integer state;
}
