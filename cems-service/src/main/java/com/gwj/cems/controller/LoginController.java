package com.gwj.cems.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.gwj.cems.pojo.dto.LoginDTO;
import com.gwj.cems.pojo.response.LoginResponse;
import com.gwj.cems.service.UserService;
import com.gwj.common.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public R login (LoginDTO loginDTO) {
        LoginResponse login = userService.login(loginDTO);
        return R.ok().data(login);
    }

    @PostMapping("/logout")
    public R logout(String guid) {
        StpUtil.logout(guid);
        return R.ok();
    }

}
