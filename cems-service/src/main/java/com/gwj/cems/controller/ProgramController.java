package com.gwj.cems.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.Program;
import com.gwj.cems.service.ProgramService;
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
    public R list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Program> aPage = programService.page(new Page<>(current, pageSize));
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
