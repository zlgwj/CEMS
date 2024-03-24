package com.gwj.cems.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwj.cems.pojo.dto.ProgramArrangeDTO;
import com.gwj.cems.pojo.entity.Program;

/**
 * 比赛项目 服务类
 *
 * @author gwj
 * @since 2024-02-27
 */
public interface ProgramService extends IService<Program> {

    void arrange(ProgramArrangeDTO params);
}
