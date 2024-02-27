package com.gwj.cems.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.RegistrationInfo;
import com.gwj.cems.service.RegistrationInfoService;
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
 * 报名信息 前端控制器
 *
 * @author gwj
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/registration-info")
public class RegistrationInfoController {


    @Autowired
    private RegistrationInfoService registrationinfoService;

    @GetMapping(value = "/page")
    public R list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<RegistrationInfo> aPage = registrationinfoService.page(new Page<>(current, pageSize));
        return R.ok().data(aPage);
    }

    @GetMapping(value = "/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok().data(registrationinfoService.getById(id));
    }

    @PostMapping(value = "/create")
    public R create(@RequestBody RegistrationInfo params) {
        registrationinfoService.save(params);
        return R.ok();
    }

    @PostMapping(value = "/delete")
    public R delete(@RequestBody List<String> ids) {
        registrationinfoService.removeBatchByIds(ids);
        return R.ok();
    }

    @PostMapping(value = "/update")
    public R update(@RequestBody RegistrationInfo params) {
        registrationinfoService.updateById(params);
        return R.ok();
    }

    @PostMapping("/signup/{id}")
    public R signup(@PathVariable String id) {
        registrationinfoService.signup(id);
        return R.ok();
    }
}
