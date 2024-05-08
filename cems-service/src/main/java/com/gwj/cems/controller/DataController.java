package com.gwj.cems.controller;

import com.gwj.cems.service.EventService;
import com.gwj.cems.service.RegistrationInfoService;
import com.gwj.common.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/data")
public class DataController {

    @Resource
    private RegistrationInfoService registrationInfoService;

    @Resource
    private EventService eventService;

    @GetMapping("/gender")
    public R getGenderData() {
        Integer[] data = registrationInfoService.collectGender();
        return R.ok().data(data);
    }

    @GetMapping("/tj")
    public R getTJData() {

        Integer[] data = registrationInfoService.collectTJ();
        return R.ok().data(data);
    }

}
