package com.gwj.cems.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.Program;
import com.gwj.cems.service.ProgramService;
import com.gwj.common.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 比赛项目 前端控制器
 *
 * @author gwj
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/program")
public class ProgramController {


    @Autowired
    private ProgramService programService;

    @GetMapping(value = "/page")
    public R list(@RequestParam(required = false) Integer current,
                  @RequestParam(required = false) Integer pageSize,
                  @RequestParam(required = false) String programName,
                  @RequestParam String eventId
    ) {
        LambdaQueryWrapper<Program> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Program::getEventGuid, eventId)
                .like(StrUtil.isNotBlank(programName), Program::getProgramName, programName);
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Program> aPage = programService.page(new Page<>(current, pageSize), wrapper);
        return R.ok().data(aPage);
    }

    @GetMapping(value = "/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok().data(programService.getById(id));
    }

    @PostMapping(value = "/create")
    public R create(@RequestBody Program params) {
        programService.save(params);
        return R.ok();
    }

    @PostMapping(value = "/delete")
    public R delete(@RequestBody List<String> ids) {
        programService.removeBatchByIds(ids);
        return R.ok();
    }

    @PostMapping(value = "/update")
    public R update(@RequestBody Program params) {
        programService.updateById(params);
        return R.ok();
    }
}
