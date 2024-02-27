package com.gwj.cems.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.Event;
import com.gwj.cems.service.EventService;
import com.gwj.common.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 赛事 前端控制器
 * </p>
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
    public R list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Event> aPage = eventService.page(new Page<>(current, pageSize));
        return R.ok().data(aPage);
    }

    @GetMapping(value = "/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok().data(eventService.getById(id));
    }

    @PostMapping(value = "/create")
    public R create(@RequestBody Event params) {
        eventService.save(params);
        return R.ok();
    }

    @PostMapping(value = "/delete/{id}")
    public R delete(@PathVariable("id") String id) {
        eventService.removeById(id);
        return R.ok();
    }

    @PostMapping(value = "/update")
    public R update(@RequestBody Event params) {
        eventService.updateById(params);
        return R.ok();
    }
}
