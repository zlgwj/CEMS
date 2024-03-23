package com.gwj.cems.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gwj.cems.pojo.entity.RegistrationInfo;
import com.gwj.cems.pojo.vo.RegistrationInfoVo;

import java.util.List;

/**
 * 报名信息 服务类
 *
 * @author gwj
 * @since 2024-02-27
 */
public interface RegistrationInfoService extends IService<RegistrationInfo> {

    void signup(String id);

    List<String> getRegisteredList(String eventId, String guid);

    Page<RegistrationInfoVo> selectPage(Page<RegistrationInfo> objectPage, String eventId, Integer state);

    Integer audit(List<String> ids);

    Integer reject(List<String> ids);

    void cancel(String id);
}
