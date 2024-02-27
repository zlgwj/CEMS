package com.gwj.cems.pojo.vo;

import com.gwj.cems.pojo.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User {

    /**
     * 名称
     */
    private String organizationName;

    /**
     * 专业
     */
    private String profession;

    /**
     * 年级
     */
    private String grade;

    /**
     * 班级
     */
    private String klass;
}
