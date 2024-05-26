package com.gwj.cems.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.CampusStyle;
import com.gwj.cems.service.CampusstyleService;
import com.gwj.common.response.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 校园风采 前端控制器
 * </p>
 *
 * @author
 * @since 2024-04-19
 */
@RestController
@RequestMapping("/campusStyle")
public class CampusStyleController {


    @Resource
    private CampusstyleService campusstyleService;

    /**
     * 上传
     *
     * @param campusStyle
     * @return
     */
    @PostMapping("/upload")
    public R uploadPhoto(@RequestBody CampusStyle campusStyle) {
        return R.ok().data(campusstyleService.save(campusStyle));
    }


    /**
     * 分页查询
     *
     * @param current
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R page(
            @RequestParam(required = false) Integer current,
            @RequestParam(required = false) Integer pageSize
    ) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        Page<CampusStyle> aPage = campusstyleService.page(new Page<>(current, pageSize));
        return R.ok().data(aPage);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody CampusStyle campusStyle) {
        return R.ok().data(campusstyleService.updateById(campusStyle));
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "delete")
    public R delete(@RequestBody List<String> ids) {
        return R.ok().data(campusstyleService.removeByIds(ids));
    }


    /**
     * 校园风采列表（按排序字段排序）
     *
     * @return
     */
    @GetMapping("/list")
    public R list() {
        return R.ok().data(campusstyleService.list(new LambdaQueryWrapper<CampusStyle>().orderBy(true, true, CampusStyle::getSort)));
    }


}
