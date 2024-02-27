package com.gwj.cems.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwj.cems.pojo.entity.Event;

/**
 * 赛事 服务类
 *
 * @author gwj
 * @since 2024-02-27
 */
public interface EventService extends IService<Event> {

    void saveEvent(Event params);
}
