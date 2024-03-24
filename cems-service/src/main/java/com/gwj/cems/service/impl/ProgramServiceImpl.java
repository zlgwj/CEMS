package com.gwj.cems.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwj.cems.mapper.ProgramMapper;
import com.gwj.cems.pojo.dto.ProgramArrangeDTO;
import com.gwj.cems.pojo.entity.Program;
import com.gwj.cems.service.ProgramService;
import com.gwj.common.enums.AM_PM_Enum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 比赛项目 服务实现类
 *
 * @author gwj
 * @since 2024-02-27
 */
@Service
public class ProgramServiceImpl extends ServiceImpl<ProgramMapper, Program> implements ProgramService {

    @Override
    public void arrange(ProgramArrangeDTO params) {
        List<Program> amList = params.getAmList();
        AtomicInteger i = new AtomicInteger();
        amList.forEach(program -> {
            update(new LambdaUpdateWrapper<Program>().eq(Program::getGuid, program.getGuid())
                    .set(Program::getPlayTime, params.getPlayTime())
                    .set(Program::getPlaySort, i.getAndIncrement())
                    .set(Program::getAmPm, AM_PM_Enum.AM.getCode())
            );
        });
        i.set(0);
        List<Program> pmList = params.getPmList();
        pmList.forEach(program -> {
            update(new LambdaUpdateWrapper<Program>().eq(Program::getGuid, program.getGuid())
                    .set(Program::getPlayTime, params.getPlayTime())
                    .set(Program::getPlaySort, i.getAndIncrement())
                    .set(Program::getAmPm, AM_PM_Enum.PM.getCode())
            );
        });

        List<Program> freeList = params.getFreeList();

        freeList.forEach(program -> {
            update(new LambdaUpdateWrapper<Program>().eq(Program::getGuid, program.getGuid())
                    .set(Program::getPlayTime, null)
                    .set(Program::getPlaySort, null)
                    .set(Program::getAmPm, null)
            );
        });


    }
}
