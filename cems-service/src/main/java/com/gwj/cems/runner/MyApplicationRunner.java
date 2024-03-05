package com.gwj.cems.runner;

import com.gwj.cems.service.EventService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Resource
    EventService eventService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        eventService.autoUpdateState();
    }
}
