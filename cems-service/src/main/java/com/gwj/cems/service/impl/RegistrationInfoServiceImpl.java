package com.gwj.cems.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwj.cems.mapper.RegistrationInfoMapper;
import com.gwj.cems.pojo.entity.Program;
import com.gwj.cems.pojo.entity.RegistrationInfo;
import com.gwj.cems.pojo.entity.User;
import com.gwj.cems.service.EventService;
import com.gwj.cems.service.ProgramService;
import com.gwj.cems.service.RegistrationInfoService;
import com.gwj.cems.service.UserService;
import com.gwj.common.enums.GenderEnum;
import com.gwj.common.exception.MyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 报名信息 服务实现类
 *
 * @author gwj
 * @since 2024-02-27
 */
@Service
public class RegistrationInfoServiceImpl extends ServiceImpl<RegistrationInfoMapper, RegistrationInfo> implements RegistrationInfoService {

    @Resource
    private UserService userService;

    @Resource
    private ProgramService programService;

    @Resource
    private EventService eventService;

    @Override
    public void signup(String id) {
        User user = (User) StpUtil.getSession().get(SaSession.USER);
        Program program = programService.getById(id);
        if (program == null) {
            throw new RuntimeException("项目信息不存在");
        }

        synchronized (this) {
            LambdaQueryWrapper<RegistrationInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RegistrationInfo::getProgramGuid, id)
                    .eq(RegistrationInfo::getUserOrganization, user.getOrganizationGuid());
            List<RegistrationInfo> list = list(wrapper);
            if (list != null && !list.isEmpty()) {
                if (list.size() >= program.getEntrantsLimit()) {
                    throw new MyException("报名人数已满");
                }
            }
            if (!Objects.equals(program.getGenderLimit(), GenderEnum.UNKNOWN.getCode())
                    && !Objects.equals(program.getGenderLimit(), user.getGender())
            ) {
                throw new MyException("性别不符合要求");
            }
            RegistrationInfo registrationInfo = new RegistrationInfo()
                    .setProgramGuid(id)
                    .setUserOrganization(user.getOrganizationGuid())
                    .setUserGuid(user.getGuid());
            save(registrationInfo);
        }
    }

    @Override
    public List<String> getRegisteredList(String eventId, String guid) {
//        该赛事的所有项目id
        List<String> collect = programService.list(new LambdaQueryWrapper<Program>().eq(Program::getEventGuid, eventId)).stream().map(Program::getGuid).collect(Collectors.toList());
//        根据项目id和用户id查询报名信息
        List<RegistrationInfo> list = list(new LambdaQueryWrapper<RegistrationInfo>().in(RegistrationInfo::getProgramGuid, collect).eq(RegistrationInfo::getUserGuid, guid));
//        提取这些报名信息的项目id
        return list.stream().map(RegistrationInfo::getProgramGuid).collect(Collectors.toList());
    }
}
