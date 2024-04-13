package com.gwj.cems.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.Notice;
import com.gwj.cems.service.NoticeService;
import com.gwj.common.enums.NoticeTypeEnum;
import com.gwj.common.response.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 公告 前端控制器
 * </p>
 *
 * @author
 * @since 2024-03-28
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @GetMapping("/list")
    public R list() {
        List<Notice> list = noticeService.getListByUser();
        return R.ok().data(list);
    }

    @PostMapping("/publish")
    public R publish(@RequestBody Notice notice) {
        noticeService.publish(notice);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return R.ok();
    }

    @GetMapping("/byId/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok().data(noticeService.getById(id));
    }

    @PostMapping("/delete")
    public R remove(@RequestBody List<String> ids) {
        noticeService.removeByIds(ids);
        return R.ok();
    }

    @GetMapping(value = "/page")
    public R list(@RequestParam(required = false) Integer current,
                  @RequestParam(required = false) Integer pageSize,
                  @RequestParam(required = false) String title
    ) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(title), Notice::getTitle, title)
                .eq(Notice::getNoticeType, NoticeTypeEnum.TEXT.getCode());
        Page<Notice> aPage = noticeService.page(new Page<>(current, pageSize), wrapper);
        return R.ok().data(aPage);
    }

}
