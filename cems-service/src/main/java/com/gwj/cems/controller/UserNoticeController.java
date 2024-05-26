package com.gwj.cems.controller;

import com.gwj.cems.service.UserNoticeService;
import com.gwj.common.response.R;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户通知 前端控制器
 * </p>
 *
 * @author
 * @since 2024-03-28
 */
@RestController
@RequestMapping("/user-notice")
public class UserNoticeController {

    @Resource
    private UserNoticeService userNoticeService;

    /**
     * 阅读通知
     *
     * @param noticeId
     * @return
     */
    @PostMapping("/read/{noticeId}")
    public R readNotice(@PathVariable String noticeId) {

        userNoticeService.readNotice(noticeId);
        return R.ok();
    }
}
