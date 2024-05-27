package com.gwj.cems.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwj.cems.pojo.entity.Event;
import com.gwj.cems.pojo.vo.TreeVo;

import java.util.List;

/**
 * 赛事 服务类
 *
 * @author gwj
 * @since 2024-02-27
 */
public interface EventService extends IService<Event> {

    void saveEvent(Event params);

    List<TreeVo> listAsTree();


    void autoUpdateState();

    List<TreeVo> listRegistrableAsTree();

    void removeEventBatchByIds(List<String> ids);
}
