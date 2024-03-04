package com.gwj.cems.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwj.cems.mapper.EventMapper;
import com.gwj.cems.pojo.entity.Event;
import com.gwj.cems.pojo.vo.TreeVo;
import com.gwj.cems.service.EventService;
import com.gwj.common.enums.EventStateEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @Override
    public List<TreeVo> listAsTree() {
        List<Event> list = list();
        HashMap<String, List<TreeVo>> map = new HashMap<>();
        list.forEach(event -> {
            List<TreeVo> treeVos = map.get(event.getYear() + "");
            if (treeVos == null) {
                ArrayList<TreeVo> values = new ArrayList<>();
                map.put(event.getYear() + "", values);
            }
            List<TreeVo> years = map.get(event.getYear() + "");
            years.add(new TreeVo(event.getEventName(), event.getGuid(), null));
        });
        ArrayList<TreeVo> result = new ArrayList<>();
        map.entrySet().forEach(entry -> {
            result.add(new TreeVo(entry.getKey() + "年", "", entry.getValue()));
        });
        return result;
    }
}
