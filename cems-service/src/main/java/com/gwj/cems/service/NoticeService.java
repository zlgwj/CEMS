package com.gwj.cems.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwj.cems.pojo.entity.Event;
import com.gwj.cems.pojo.entity.Notice;

import java.util.List;

/**
 * <p>
 * 公告 服务类
 * </p>
 *
 * @author
 * @since 2024-03-28
 */
public interface NoticeService extends IService<Notice> {

    List<Notice> getListByUser();

    void createLinkedNotice(Event params);

    void publish(Notice notice);
}
