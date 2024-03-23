package com.gwj.cems.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegistrationInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField("GUID")
    private String guid;

    /**
     * 用户ID
     */
    @TableField("USER_GUID")
    private String userGuid;

    private String userName;

    @TableField("USER_ORGANIZATION")
    private String userOrganization;

    private String organizationName;

    /**
     * 比赛项目ID
     */
    @TableField("PROGRAM_GUID")
    private String programGuid;

    private String programName;

    /**
     * 报名状态
     */
    @TableField("STATE")
    private Integer state;
}
