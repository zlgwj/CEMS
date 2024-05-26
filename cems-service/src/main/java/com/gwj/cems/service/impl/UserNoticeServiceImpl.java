package com.gwj.cems.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwj.cems.mapper.UserNoticeMapper;
import com.gwj.cems.pojo.entity.User;
import com.gwj.cems.pojo.entity.UserNotice;
import com.gwj.cems.service.UserNoticeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户通知 服务实现类
 * </p>
 *
 * @author
 * @since 2024-03-28
 */
@Service
public class UserNoticeServiceImpl extends ServiceImpl<UserNoticeMapper, UserNotice> implements UserNoticeService {

    @Override
    public void readNotice(String noticeId) {
        User user = (User) StpUtil.getSession().get(SaSession.USER);

//        新增一个 userNotice 信息表明用户已阅读notice
        UserNotice userNotice = new UserNotice();
        userNotice.setUserGuid(user.getGuid());
        userNotice.setNoticeGuid(noticeId);
        save(userNotice);
    }
}
