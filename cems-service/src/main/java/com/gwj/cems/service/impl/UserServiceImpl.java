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
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginDTO.getUsername());
        User user = getOne(queryWrapper);
        if (user == null) {
            log.error("登录失败，用户名或密码错误");
            throw new MyException(ResultEnum.LOGIN_ERROR.getCode(), "用户名或密码错误");
        }

//        SecretKey secretKey = SecureUtil.generateKey(key);
//        byte[] algorithm = secretKey.getEncoded();
//        log.info("密钥：{}", algorithm);
        String password = decryptPassword(user.getPassword());

        log.info("解密后的password: {}", password);

//        md5摘要
        MD5 md5 = MD5.create();
        String digestedPassword = md5.digestHex(password);
//        String digestedPassword = password;

        log.info("password:{}", loginDTO.getPassword());
        log.info("摘要后password: {}",digestedPassword);

        if (!digestedPassword.equals(loginDTO.getPassword())) {
            log.error("登录失败，用户名或密码错误");
            throw new MyException(ResultEnum.LOGIN_ERROR.getCode(),"用户名或密码错误");
        }
        StpUtil.login(user.getGuid());
        log.info("登录成功，token:{}", StpUtil.getTokenValue());
        StpUtil.getSession().set(SaSession.USER, user);
        return new LoginResponse(user.getUsername(), user.getName(), StpUtil.getTokenValue());

    }

    @Override
    public void update(User params) {
        if (StrUtil.isBlank(params.getPassword())) {
            params.setPassword(null);
            updateById(params);
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
        String password = encryptPassword(defaultPassword);
        byId.setPassword(password);
        updateById(byId);
    }

    @Override
    public void saveUser(User params) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, params.getUsername());
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
        BeanUtil.copyProperties(user, userVO);
        BeanUtil.copyProperties(organization, userVO);
        userVO.setOrganizationName(organization.getName());
        return userVO;
    }

    private String encryptPassword(String password) {
        SM4 sm4 = SmUtil.sm4(HexUtil.decodeHex(key));
        return sm4.encryptHex(password);
    }

    private String decryptPassword(String password) {
        SM4 sm4 = SmUtil.sm4(HexUtil.decodeHex(key));
        return sm4.decryptStr(password);
    }

}
