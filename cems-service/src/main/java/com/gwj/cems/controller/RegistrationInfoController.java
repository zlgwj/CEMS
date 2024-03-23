package com.gwj.cems.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.User;
import com.gwj.cems.pojo.vo.RegistrationInfoVo;
import com.gwj.cems.service.RegistrationInfoService;
import com.gwj.common.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R list(@RequestParam(required = false) Integer current,
                  @RequestParam(required = false) Integer pageSize,
                  @RequestParam(required = false) Integer state,
                  @RequestParam String eventId) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<RegistrationInfoVo> aPage = registrationinfoService.selectPage(new Page<>(current, pageSize), eventId, state);
        return R.ok().data(aPage);
    }

    @PostMapping("/signup/{id}")
    public R signup(@PathVariable String id) {
        registrationinfoService.signup(id);
        return R.ok();
    }

    @GetMapping(value = "/registered/list/{eventId}")
    public R getRegisteredList(@PathVariable String eventId) {
        User user = (User) StpUtil.getSession().get(SaSession.USER);
        List<String> ids = registrationinfoService.getRegisteredList(eventId, user.getGuid());
        return R.ok().data(ids);
    }

    @PostMapping(value = "/audit")
    public R audit(@RequestBody List<String> ids) {
        Integer audit = registrationinfoService.audit(ids);
        return R.ok().data(audit);
    }

    @PostMapping(value = "/reject")
    public R reject(@RequestBody List<String> ids) {
        Integer reject = registrationinfoService.reject(ids);
        return R.ok().data(reject);
    }

    @PostMapping(value = "/cancel/{id}")
    public R cancel(@PathVariable String id) {
        registrationinfoService.cancel(id);
        return R.ok();
    }
}
