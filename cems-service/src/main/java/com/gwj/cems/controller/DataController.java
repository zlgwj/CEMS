package com.gwj.cems.controller;

import com.gwj.cems.service.EventService;
import com.gwj.cems.service.ProgramService;
import com.gwj.cems.service.RegistrationInfoService;
import com.gwj.common.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 数据统计（数据可视化）
 */
@RestController
@RequestMapping("/data")
public class DataController {

    @Resource
    private RegistrationInfoService registrationInfoService;

    @Resource
    private EventService eventService;

    @Resource
    private ProgramService programService;

    /**
     * 根据性别统计报名信息
     */

    @GetMapping("/gender")
    public R getGenderData() {
        Integer[] data = registrationInfoService.collectGender();
        return R.ok().data(data);
    }

    /**
     * 按比赛类型（田径赛）统计报名信息
     *
     * @return
     */
    @GetMapping("/tj")
    public R getTJData() {

        Integer[] data = registrationInfoService.collectTJ();
        return R.ok().data(data);
    }

    @GetMapping("/all")
    public R getAll() {
        long count = registrationInfoService.count();
        return R.ok().data(count);
    }

    @GetMapping("/allProgram")
    public R getAllProgram() {
        long count = programService.count();
        return R.ok().data(count);
    }
}
