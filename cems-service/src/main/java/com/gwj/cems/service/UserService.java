package com.gwj.cems.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwj.cems.pojo.dto.LoginDTO;
import com.gwj.cems.pojo.entity.User;
import com.gwj.cems.pojo.response.LoginResponse;
import com.gwj.cems.pojo.vo.UserVO;

/**
 * 用户 服务类
 *
 * @author gwj
 * @since 2024-02-27
 */
public interface UserService extends IService<User> {
    LoginResponse login(LoginDTO loginDTO);

    void update(User params);

    void resetPassword(Long id);

    void saveUser(User user);

    UserVO getUserDetail(String id);

    void updateUser(User params);
}
