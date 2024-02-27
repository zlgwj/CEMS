package com.gwj.cems.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwj.cems.pojo.entity.Role;
import com.gwj.cems.mapper.RoleMapper;
import com.gwj.cems.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色管理 服务实现类
 * </p>
 *
 * @author gwj
 * @since 2024-02-27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
