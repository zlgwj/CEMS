package com.gwj.cems.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwj.cems.pojo.entity.Event;
import com.gwj.cems.mapper.EventMapper;
import com.gwj.cems.service.EventService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 赛事 服务实现类
 * </p>
 *
 * @author gwj
 * @since 2024-02-27
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {

}
