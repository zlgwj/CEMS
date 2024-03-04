package com.gwj.cems.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.Event;
import com.gwj.cems.pojo.vo.TreeVo;
import com.gwj.cems.service.EventService;
import com.gwj.common.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 赛事 前端控制器
 *
 * @author gwj
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/event")
public class EventController {


    @Autowired
    private EventService eventService;

    @GetMapping(value = "/page")
    public R list(@RequestParam(required = false) Integer current,
                  @RequestParam(required = false) Integer pageSize,
                  @RequestParam(required = false) Integer state,
                  @RequestParam(required = false) String eventName
    ) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        LambdaQueryWrapper<Event> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(eventName), Event::getEventName, eventName)
                .eq(state != null, Event::getState, state);
        Page<Event> aPage = eventService.page(new Page<>(current, pageSize), wrapper);
        return R.ok().data(aPage);
    }

    @GetMapping("/tree")
    public R listAsTree() {
        List<TreeVo> treeVoList = eventService.listAsTree();
        return R.ok().data(treeVoList);
    }

    @GetMapping(value = "/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok().data(eventService.getById(id));
    }

    @PostMapping(value = "/create")
    public R create(@RequestBody Event params) {
        eventService.saveEvent(params);
        return R.ok();
    }

    @PostMapping(value = "/delete")
    public R delete(@RequestBody List<String> ids) {
        eventService.removeBatchByIds(ids);
        return R.ok();
    }

    @PostMapping(value = "/update")
    public R update(@RequestBody Event params) {
        eventService.updateById(params);
        return R.ok();
    }
}
