package com.gwj.cems.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwj.cems.mapper.RegistrationInfoMapper;
import com.gwj.cems.pojo.entity.Event;
import com.gwj.cems.pojo.entity.Program;
import com.gwj.cems.pojo.entity.RegistrationInfo;
import com.gwj.cems.pojo.entity.User;
import com.gwj.cems.pojo.vo.RegistrationInfoVo;
import com.gwj.cems.service.EventService;
import com.gwj.cems.service.ProgramService;
import com.gwj.cems.service.RegistrationInfoService;
import com.gwj.cems.service.UserService;
import com.gwj.common.enums.*;
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
            Event event = eventService.getById(program.getEventGuid());
            Integer count = baseMapper.countConcurrent(event.getGuid(), user.getGuid());
            if (event.getMaxConcurrent() < count) {
                throw new MyException("兼项过多");
            }

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
                    .setState(RegistrationStateEnum.SIGN_UP.getCode())
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

    @Override
    public Page<RegistrationInfoVo> selectPage(Page<RegistrationInfo> page, String eventId, Integer state) {
//        该赛事的所有项目id
        User user = (User) StpUtil.getSession().get(SaSession.USER);
        List<String> collect = programService.list(new LambdaQueryWrapper<Program>().eq(Program::getEventGuid, eventId)).stream().map(Program::getGuid).collect(Collectors.toList());

        QueryWrapper<RegistrationInfo> wrapper = new QueryWrapper<RegistrationInfo>()
                .in("r.PROGRAM_GUID", collect)
                .eq("r.STATE", state)
                .eq("r.IS_DELETED", DeleteEnum.NOT_DELETE.getCode())
                .orderByDesc("r.PROGRAM_GUID");

        if (user.getRole().equals(RoleEnum.ADMIN.getCode())) {
//        根据项目id和 查询报名信息
            return baseMapper.pageVo(page, wrapper);
        } else {
//        根据项目id和 查询报名信息
            return baseMapper.pageVo(page, wrapper.eq("r.USER_ORGANIZATION", user.getOrganizationGuid()));
        }
    }

    /**
     * 审核
     *
     * @param ids 选中的报名信息
     */
    @Override
    public Integer audit(List<String> ids) {
//        获取当前用户 （角色）
        User user = (User) StpUtil.getSession().get(SaSession.USER);
        Integer role = user.getRole();

//        根据ids 查询所有的报名信息 （需要判断审核状态）
        List<RegistrationInfo> list = list(new LambdaQueryWrapper<RegistrationInfo>().in(!ids.isEmpty(), RegistrationInfo::getGuid, ids));

        if (role.equals(RoleEnum.ADMIN.getCode())) {
            List<RegistrationInfo> collect = list.stream().filter(info -> Objects.equals(info.getState(), RegistrationStateEnum.FIRST_INSTANCED.getCode()))
                    .peek(info -> info.setState(RegistrationStateEnum.SIGN_UP_SUCCEED.getCode())).collect(Collectors.toList());
            updateBatchById(collect);
            return collect.size();
        } else if (role.equals(RoleEnum.AUDITOR.getCode())) {
            List<RegistrationInfo> collect = list.stream().filter(info -> Objects.equals(info.getState(), RegistrationStateEnum.SIGN_UP.getCode()))
                    .peek(info -> info.setState(RegistrationStateEnum.FIRST_INSTANCED.getCode())).collect(Collectors.toList());
            updateBatchById(collect);
            return collect.size();
        } else {
            throw new MyException("权限不足");
        }
    }

    @Override
    public Integer reject(List<String> ids) {
//        获取当前用户 （角色）
        User user = (User) StpUtil.getSession().get(SaSession.USER);
        Integer role = user.getRole();

//        根据ids 查询所有的报名信息 （需要判断审核状态）
        List<RegistrationInfo> list = list(new LambdaQueryWrapper<RegistrationInfo>().in(!ids.isEmpty(), RegistrationInfo::getGuid, ids));

        if (role.equals(RoleEnum.ADMIN.getCode())) {
            List<RegistrationInfo> collect = list.stream().filter(info -> Objects.equals(info.getState(), RegistrationStateEnum.FIRST_INSTANCED.getCode()))
                    .peek(info -> info.setState(RegistrationStateEnum.FINAL_REJECT.getCode())).collect(Collectors.toList());
            updateBatchById(collect);
            return collect.size();
        } else if (role.equals(RoleEnum.AUDITOR.getCode())) {
            List<RegistrationInfo> collect = list.stream().filter(info -> Objects.equals(info.getState(), RegistrationStateEnum.SIGN_UP.getCode()))
                    .peek(info -> info.setState(RegistrationStateEnum.FIRST_INSTANCED_REJECT.getCode())).collect(Collectors.toList());
            updateBatchById(collect);
            return collect.size();
        } else {
            throw new MyException("权限不足");
        }
    }

    @Override
    public void cancel(String id) {
        User user = (User) StpUtil.getSession().get(SaSession.USER);
        RegistrationInfo byId = getById(id);
        if (byId.getUserGuid().equals(user.getGuid())) {
            removeById(id);
            return;
        }
        throw new MyException("权限不足");
    }

    @Override
    public Integer[] collectGender() {

        List<String> collect1 = programService.list(new LambdaQueryWrapper<Program>().eq(Program::getGenderLimit, GenderEnum.MALE.getCode())).stream().map(Program::getGuid).collect(Collectors.toList());
        List<String> collect2 = programService.list(new LambdaQueryWrapper<Program>().eq(Program::getGenderLimit, GenderEnum.FEMALE.getCode())).stream().map(Program::getGuid).collect(Collectors.toList());

        long count1 = count(new LambdaQueryWrapper<RegistrationInfo>().in(RegistrationInfo::getProgramGuid, collect1));
        long count2 = count(new LambdaQueryWrapper<RegistrationInfo>().in(RegistrationInfo::getProgramGuid, collect2));
        return new Integer[]{(int) count1, (int) count2};
    }

    @Override
    public Integer[] collectTJ() {
        List<String> collect1 = programService.list(new LambdaQueryWrapper<Program>().eq(Program::getProgramType, ProgramTypeEnum.FIELD_EVENT.getCode())).stream().map(Program::getGuid).collect(Collectors.toList());
        List<String> collect2 = programService.list(new LambdaQueryWrapper<Program>().eq(Program::getProgramType, ProgramTypeEnum.TRACK_EVENT.getCode())).stream().map(Program::getGuid).collect(Collectors.toList());
        List<String> collect3 = programService.list(new LambdaQueryWrapper<Program>().eq(Program::getProgramType, ProgramTypeEnum.MASS_EVENT.getCode())).stream().map(Program::getGuid).collect(Collectors.toList());
        long count1 = 0;
        if (!collect1.isEmpty()) {
            count1 = count(new LambdaQueryWrapper<RegistrationInfo>().in(RegistrationInfo::getProgramGuid, collect1));
        }
        long count2 = 0;
        if (!collect2.isEmpty()) {
            count2 = count(new LambdaQueryWrapper<RegistrationInfo>().in(RegistrationInfo::getProgramGuid, collect2));
        }
        long count3 = 0;
        if (!collect3.isEmpty()) {
            count3 = count(new LambdaQueryWrapper<RegistrationInfo>().in(RegistrationInfo::getProgramGuid, collect3));
        }
        return new Integer[]{(int) count1, (int) count2, (int) count3};
    }
}
