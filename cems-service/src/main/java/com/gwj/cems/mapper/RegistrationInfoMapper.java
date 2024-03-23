package com.gwj.cems.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwj.cems.pojo.entity.RegistrationInfo;
import com.gwj.cems.pojo.vo.RegistrationInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * 报名信息 Mapper 接口
 *
 * @author gwj
 * @since 2024-02-27
 */
public interface RegistrationInfoMapper extends BaseMapper<RegistrationInfo> {

    Page<RegistrationInfoVo> pageVo(Page<RegistrationInfo> page, @Param(Constants.WRAPPER) Wrapper<RegistrationInfo> wrapper);

    Integer countConcurrent(@Param("eventGuid") String eventGuid, @Param("userGuid") String userGuid);
}
