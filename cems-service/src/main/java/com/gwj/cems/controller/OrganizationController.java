package com.gwj.cems.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.Organization;
import com.gwj.cems.service.OrganizationService;
import com.gwj.common.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织 前端控制器
 *
 * @author
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {


    @Autowired
    private OrganizationService organizationService;

    /**
     * 分页查询所有组织
     *
     * @param current
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/page")
    public R list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Organization> aPage = organizationService.page(new Page<>(current, pageSize));
        return R.ok().data(aPage);
    }

    /**
     * 根据id查询组织
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public R getById(@PathVariable("id") String id) {
        return R.ok().data(organizationService.getById(id));
    }

    /**
     * 创建组织
     * @param params
     * @return
     */
    @PostMapping(value = "/create")
    public R create(@RequestBody Organization params) {
        organizationService.save(params);
        return R.ok().message("created successfully");
    }

    /**
     * 删除组织
     * @param ids
     * @return
     */
    @PostMapping(value = "/delete")
    public R delete(@RequestBody() List<String> ids) {
        organizationService.removeBatchByIds(ids);
        return R.ok().message("deleted successfully");
    }

    /**
     * 查询所有组织
     * @return
     */
    @GetMapping(value = "/list")
    public R allOrganization() {
        return R.ok().data(organizationService.list());
    }

    /**
     * 更新组织
     * @param params
     * @return
     */
    @PostMapping(value = "/update")
    public R update(@RequestBody Organization params) {
        organizationService.updateById(params);
        return R.ok().message("updated successfully");
    }
}
