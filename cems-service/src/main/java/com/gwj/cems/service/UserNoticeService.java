package com.gwj.cems.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwj.cems.pojo.entity.UserNotice;

/**
 * <p>
 * 用户通知 服务类
 * </p>
 *
 * @author
 * @since 2024-03-28
 */
public interface UserNoticeService extends IService<UserNotice> {

    void readNotice(String noticeId);
}
