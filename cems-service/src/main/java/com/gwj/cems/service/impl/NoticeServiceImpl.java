package com.gwj.cems.service.impl;


import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwj.cems.mapper.NoticeMapper;
import com.gwj.cems.pojo.entity.Event;
import com.gwj.cems.pojo.entity.Notice;
import com.gwj.cems.pojo.entity.User;
import com.gwj.cems.pojo.entity.UserNotice;
import com.gwj.cems.service.NoticeService;
import com.gwj.cems.service.UserNoticeService;
import com.gwj.common.enums.NoticeTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 公告 服务实现类
 * </p>
 *
 * @author
 * @since 2024-03-28
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    private final String TEXT_DESC = "查看详情";
    @Resource
    private UserNoticeService usernoticeService;
    @Value("${notice.title}")
    private String title;
    @Value("${notice.desc}")
    private String desc;
    @Value("${notice.link}")
    private String link;

    @Override
    public List<Notice> getListByUser() {
        User user = (User) StpUtil.getSession().get(SaSession.USER);
        LambdaQueryWrapper<UserNotice> userNoticeWrapper = new LambdaQueryWrapper<>();
        userNoticeWrapper.eq(UserNotice::getUserGuid, user.getGuid());
        List<String> notices = usernoticeService.list(userNoticeWrapper).stream().map(UserNotice::getNoticeGuid).collect(Collectors.toList());
        if (notices.isEmpty()) {
            return list();
        }
        LambdaQueryWrapper<Notice> noticeLambdaQueryWrapper = new LambdaQueryWrapper<Notice>().notIn(Notice::getGuid, notices);
        return list(noticeLambdaQueryWrapper);
    }

    @Override
    public void createLinkedNotice(Event params) {
        Notice notice = new Notice();
        notice.setNoticeType(NoticeTypeEnum.LINK.getCode());
        notice.setTitle(String.format(title, params.getEventName()));
        notice.setDescription(desc);
        notice.setLink(link);
        save(notice);
    }

    @Override
    public void publish(Notice notice) {
        notice.setNoticeType(NoticeTypeEnum.TEXT.getCode());
        notice.setDescription(TEXT_DESC);
        save(notice);
    }
}
