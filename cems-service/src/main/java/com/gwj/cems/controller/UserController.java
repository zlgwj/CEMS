package com.gwj.cems.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.User;
import com.gwj.cems.service.UserService;
import com.gwj.common.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户 前端控制器
 *
 * @author gwj
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping(value = "/page")
    public R list(@RequestParam(required = false) Integer current,
                  @RequestParam(required = false) Integer pageSize,
                  @RequestParam(required = false) String name,
                  @RequestParam(required = false) String phone,
                  @RequestParam(required = false) String username
    ) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(name), User::getName, name)
                .eq(StrUtil.isNotBlank(phone), User::getPhone, phone)
                .eq(StrUtil.isNotBlank(username), User::getUsername, username).orderBy(true, true, User::getGuid);
        Page<User> aPage = userService.page(new Page<>(current, pageSize), wrapper);
        return R.ok().data(aPage);
    }

    @GetMapping(value = "/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok().data(userService.getUserDetail(id));
    }

    @PostMapping(value = "/create")
    public R create(@RequestBody User params) {
        userService.saveUser(params);
        return R.ok();
    }

    @PostMapping(value = "/delete")
    public R delete(@RequestBody List<String> ids) {
        userService.removeBatchByIds(ids);
        return R.ok();
    }

    @PostMapping(value = "/update")
    public R update(@RequestBody User params) {
        userService.updateUser(params);
        return R.ok();
    }

    @GetMapping(value = "/list")
    public R getUserList() {
        return R.ok().data(userService.list());
    }
}
