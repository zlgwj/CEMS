package com.gwj.cems.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.SM4;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwj.cems.mapper.UserMapper;
import com.gwj.cems.pojo.dto.LoginDTO;
import com.gwj.cems.pojo.entity.Organization;
import com.gwj.cems.pojo.entity.User;
import com.gwj.cems.pojo.response.LoginResponse;
import com.gwj.cems.pojo.vo.UserVO;
import com.gwj.cems.service.OrganizationService;
import com.gwj.cems.service.UserService;
import com.gwj.common.enums.ResultEnum;
import com.gwj.common.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户 服务实现类
 *
 * @author gwj
 * @since 2024-02-27
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${encrypt.sm4-key}")
    private String key;

    @Value("${encrypt.default-password}")
    private String defaultPassword;

    @Resource
    private OrganizationService organizationService;

    @Override
    public LoginResponse login(LoginDTO loginDTO) {
//        根据登录的username查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginDTO.getUsername());
        User user = getOne(queryWrapper);
        if (user == null) {
            log.error("登录失败，用户名或密码错误");
            throw new MyException(ResultEnum.LOGIN_ERROR.getCode(), "用户名或密码错误");
        }

//        解密查询出来的密码
        String password = decryptPassword(user.getPassword());

        log.info("解密后的password: {}", password);

//        将解密后的密码进行md5摘要
        MD5 md5 = MD5.create();
        String digestedPassword = md5.digestHex(password);


        log.info("password:{}", loginDTO.getPassword());
        log.info("摘要后password: {}",digestedPassword);

//        将md5后的密码和登录的密码进行比较
        if (!digestedPassword.equals(loginDTO.getPassword())) {
            log.error("登录失败，用户名或密码错误");
            throw new MyException(ResultEnum.LOGIN_ERROR.getCode(),"用户名或密码错误");
        }
        StpUtil.login(user.getGuid());
        log.info("登录成功，token:{}", StpUtil.getTokenValue());
        StpUtil.getSession().set(SaSession.USER, user);
        return new LoginResponse(user.getUsername(), user.getName(), StpUtil.getTokenValue(), user.getRole());

    }

    @Override
    public void update(User params) {
//        如果没有更新密码，则不更新密码
        if (StrUtil.isBlank(params.getPassword())) {
            params.setPassword(null);
            updateById(params);
//            如果有密码，则先将密码加密再存储
        }else {
            params.setPassword(encryptPassword(params.getPassword()));
            updateById(params);
        }
    }

    @Override
    public void resetPassword(Long id) {
        User byId = getById(id);
        if (byId == null) {
            throw new MyException(ResultEnum.NOT_FOUND.getCode(), "用户不存在");
        }
//        将默认密码加密后更新
        String password = encryptPassword(defaultPassword);
        byId.setPassword(password);
        updateById(byId);
    }

    @Override
    public void saveUser(User params) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, params.getUsername());
//        查询用户名是否存在
        if (getOne(userQueryWrapper) != null) {
            throw new MyException(ResultEnum.PARAM_ERROR.getCode(), "用户已存在");
        }
        params.setPassword(encryptPassword(params.getPassword()));
        save(params);
    }

    @Override
    public UserVO getUserDetail(String id) {
        User user = getById(id);
        String organizationGuid = user.getOrganizationGuid();
        Organization organization = organizationService.getById(organizationGuid);
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(organization, userVO);
        BeanUtil.copyProperties(user, userVO);
        if (organization != null)
            userVO.setOrganizationName(organization.getName());
        return userVO;
    }

    @Override
    public void updateUser(User params) {
        User user = getById(params.getGuid());
        if (!user.getPassword().equals(params.getPassword())) {
            params.setPassword(encryptPassword(params.getPassword()));
        }
        updateById(params);
    }

    /**
     * 使用国密sm4算法加密
     *
     * @param password
     * @return
     */
    private String encryptPassword(String password) {
        SM4 sm4 = SmUtil.sm4(HexUtil.decodeHex(key));
        return sm4.encryptHex(password);
    }

    /**
     * 使用国密sm4算法解密
     * @param password
     * @return
     */
    private String decryptPassword(String password) {
        SM4 sm4 = SmUtil.sm4(HexUtil.decodeHex(key));
        return sm4.decryptStr(password);
    }

}
