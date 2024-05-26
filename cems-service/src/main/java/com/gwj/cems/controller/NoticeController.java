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

    /**
     * 根据登录用户获取未读的公告列表
     *
     * @return
     */
    @GetMapping("/list")
    public R list() {
        List<Notice> list = noticeService.getListByUser();
        return R.ok().data(list);
    }

    /**
     * 发布公告
     *
     * @param notice
     * @return
     */
    @PostMapping("/publish")
    public R publish(@RequestBody Notice notice) {
        noticeService.publish(notice);
        return R.ok();
    }

    /**
     * 修改公告
     * @param notice
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return R.ok();
    }

    /**
     * 根据id获取公告
     * @param id
     * @return
     */
    @GetMapping("/byId/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok().data(noticeService.getById(id));
    }

    /**
     * 删除公告
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public R remove(@RequestBody List<String> ids) {
        noticeService.removeByIds(ids);
        return R.ok();
    }

    /**
     * 分页查询所有公告
     * @param current
     * @param pageSize
     * @param title
     * @return
     */
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
