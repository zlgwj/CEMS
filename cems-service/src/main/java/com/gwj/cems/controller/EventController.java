package com.gwj.cems.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.Event;
import com.gwj.cems.pojo.vo.TreeVo;
import com.gwj.cems.service.EventService;
import com.gwj.cems.service.NoticeService;
import com.gwj.common.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 赛事 前端控制器
 *
 * @author gwj
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/event")
@Slf4j
public class EventController {


    @Autowired
    private EventService eventService;

    @Resource
    private NoticeService noticeService;

    /**
     * 分页查询所有赛事
     *
     * @param current
     * @param pageSize
     * @param state
     * @param eventName
     * @return
     */
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

    /**
     * 按树形查询所有赛事
     *
     * @return
     */
    @GetMapping("/tree")
    public R listAsTree() {
        List<TreeVo> treeVoList = eventService.listAsTree();
        return R.ok().data(treeVoList);
    }

    /**
     * 按树形查询所有可报名赛事
     *
     * @return
     */
    @GetMapping("/tree/registrable")
    public R listRegistrableAsTree() {
        List<TreeVo> treeVoList = eventService.listRegistrableAsTree();
        return R.ok().data(treeVoList);
    }

    /**
     * 根据id获取赛事
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok().data(eventService.getById(id));
    }

    /**
     * 创建赛事
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/create")
    public R create(@RequestBody Event params) {
        eventService.saveEvent(params);
        noticeService.createLinkedNotice(params);
        return R.ok();
    }

    /**
     * 根据id列表批量删除赛事
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/delete")
    public R delete(@RequestBody List<String> ids) {
        eventService.removeEventBatchByIds(ids);
        return R.ok();
    }

    /**
     * 根性赛事
     *
     * @param params
     * @return
     */
    @PostMapping(value = "/update")
    public R update(@RequestBody Event params) {
        eventService.updateById(params);
        return R.ok();
    }
}
