package com.gwj.cems.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.Organization;
import com.gwj.cems.service.OrganizationService;
import com.gwj.common.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/")
    public ResponseEntity<Page<Organization>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<Organization> aPage = organizationService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Organization> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(organizationService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody Organization params) {
        organizationService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        organizationService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    public R allOrganization() {
        return R.ok().data(organizationService.list());
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Organization params) {
        organizationService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}