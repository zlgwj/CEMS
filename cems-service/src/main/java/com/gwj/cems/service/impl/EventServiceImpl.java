package com.gwj.cems.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwj.cems.mapper.EventMapper;
import com.gwj.cems.pojo.entity.Event;
import com.gwj.cems.service.EventService;
import com.gwj.common.enums.EventStateEnum;
import org.springframework.stereotype.Service;

/**
 * 赛事 服务实现类
 *
 * @author gwj
 * @since 2024-02-27
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {

    @Override
    public void saveEvent(Event params) {
        params.setYear(DateUtil.year(params.getEventStart()));
        params.setState(EventStateEnum.APPROVAL.getCode());
        save(params);
    }
}
