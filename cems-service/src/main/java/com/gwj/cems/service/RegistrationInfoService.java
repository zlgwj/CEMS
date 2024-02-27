package com.gwj.cems.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwj.cems.pojo.entity.RegistrationInfo;

/**
 * 报名信息 服务类
 *
 * @author gwj
 * @since 2024-02-27
 */
public interface RegistrationInfoService extends IService<RegistrationInfo> {

    void signup(String id);
}
