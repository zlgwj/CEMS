package com.gwj.cems.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.dto.ProgramArrangeDTO;
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

    /**
     * 分页查询
     *
     * @param current
     * @param pageSize
     * @param programName
     * @param programType
     * @param eventId
     * @return
     */
    @GetMapping(value = "/page")
    public R list(@RequestParam(required = false) Integer current,
                  @RequestParam(required = false) Integer pageSize,
                  @RequestParam(required = false) String programName,
                  @RequestParam(required = false) Integer programType,
                  @RequestParam String eventId
    ) {
        LambdaQueryWrapper<Program> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Program::getEventGuid, eventId)
                .eq(programType != null && programType != 0, Program::getProgramType, programType)
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

    /**
     * 根据赛事和项目类型查询比赛项目
     * @param eventId
     * @param type
     * @return
     */
    @GetMapping(value = "/list/{type}/{eventId}")
    public R getProgramList(@PathVariable String eventId, @PathVariable Integer type) {
        return R.ok().data(programService.list(new LambdaQueryWrapper<Program>().eq(Program::getEventGuid, eventId).eq(Program::getProgramType, type)));
    }

    /**
     * 根据id查询比赛项目
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok().data(programService.getById(id));
    }

    /**
     * 创建比赛项目
     * @param params
     * @return
     */
    @PostMapping(value = "/create")
    public R create(@RequestBody Program params) {
        programService.save(params);
        return R.ok();
    }

    /**
     * 删除比赛项目
     * @param ids
     * @return
     */
    @PostMapping(value = "/delete")
    public R delete(@RequestBody List<String> ids) {
        programService.removeBatchByIds(ids);
        return R.ok();
    }

    /**
     * 更新比赛项目
     * @param params
     * @return
     */
    @PostMapping(value = "/update")
    public R update(@RequestBody Program params) {
        programService.updateById(params);
        return R.ok();
    }

    /**
     * 编排赛事项目赛程
     * @param params
     * @return
     */
    @PostMapping(value = "/arrange")
    public R arrange(@RequestBody ProgramArrangeDTO params) {
        programService.arrange(params);
        return R.ok();
    }
}
