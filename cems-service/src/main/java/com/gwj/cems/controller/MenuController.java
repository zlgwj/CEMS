package com.gwj.cems.controller;


import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.gwj.cems.pojo.entity.User;
import com.gwj.common.constant.RedisKeyConstant;
import com.gwj.common.response.R;
import com.gwj.common.utils.RedisUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {


    @Resource
    RedisUtil redisUtil;

    @GetMapping(value = "/get")
    public R getMenu() {

        User user = (User) StpUtil.getSession().get(SaSession.USER);
        Object o = redisUtil.get(RedisKeyConstant.MENU_KEY + user.getRole());
        System.out.println(o);
        return R.ok().data(o);
    }
}
