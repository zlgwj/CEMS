package com.gwj.cems.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.Role;
import com.gwj.cems.service.RoleService;
import com.gwj.common.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理 前端控制器
 *
 * @author gwj
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/page")
    public R list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Role> aPage = roleService.page(new Page<>(current, pageSize));
        return R.ok().data(aPage);
    }

    @GetMapping(value = "/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok().data(roleService.getById(id));
    }

    @PostMapping(value = "/create")
    public R create(@RequestBody Role params) {
        roleService.save(params);
        return R.ok();
    }

    @PostMapping(value = "/delete")
    public R delete(@RequestBody List<String> ids) {
        roleService.removeBatchByIds(ids);
        return R.ok();
    }

    @GetMapping(value = "/list")
    public R getAllRole() {
        return R.ok().data(roleService.list());
    }

    @PostMapping(value = "/update")
    public R update(@RequestBody Role params) {
        roleService.updateById(params);
        return R.ok();
    }
}
