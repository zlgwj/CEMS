package com.gwj.cems.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gwj.cems.mapper.EventMapper;
import com.gwj.cems.pojo.entity.Event;
import com.gwj.cems.pojo.entity.Program;
import com.gwj.cems.pojo.vo.TreeVo;
import com.gwj.cems.service.EventService;
import com.gwj.cems.service.ProgramService;
import com.gwj.common.enums.EventStateEnum;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 赛事 服务实现类
 *
 * @author gwj
 * @since 2024-02-27
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {


    @Resource
    private ProgramService programService;

    /**
     * 保存赛事
     *
     * @param params
     */

    @Override
    public void saveEvent(Event params) {
//        填充年份
        params.setYear(DateUtil.year(params.getEventStart()));
//        填充状态
        params.setState(EventStateEnum.APPROVAL.getCode());
        save(params);
    }


    /**
     * 树形查询所有赛事
     * @return
     */
    @Override
    public List<TreeVo> listAsTree() {
//        查询所有赛事
        List<Event> list = list();
//        按赛事的年度分组
        HashMap<String, List<TreeVo>> map = new HashMap<>();
        list.forEach(event -> {
            List<TreeVo> treeVos = map.get(event.getYear() + "");
//            若该年度在map中找不到则创建一个
            if (treeVos == null) {
                ArrayList<TreeVo> values = new ArrayList<>();
                map.put(event.getYear() + "", values);
            }
//            获取map中该赛事年度的list，将该赛事添加进去
            List<TreeVo> years = map.get(event.getYear() + "");
            years.add(new TreeVo(event.getEventName(), event.getGuid(), null));
        });
//        结果集
        ArrayList<TreeVo> result = new ArrayList<>();
//        将map的数据添加到result中
        map.entrySet().forEach(entry -> {
            result.add(new TreeVo(entry.getKey() + "年", "", entry.getValue()));
        });
        return result;
    }

    //    每天凌晨1点执行
    @Scheduled(cron = "0 0 1 * * ?")
    @Override
    public void autoUpdateState() {
        List<Event> list = list();
        for (Event event : list) {
            boolean isUpdate = false;
            if (event.getRegisterStart().getTime() < System.currentTimeMillis() && event.getState().equals(EventStateEnum.APPROVAL.getCode())) {
                event.setState(EventStateEnum.SIGN_UP.getCode());
                isUpdate = true;
            }
            if (event.getRegisterEnd().getTime() < System.currentTimeMillis() && event.getState().equals(EventStateEnum.SIGN_UP.getCode())) {
                event.setState(EventStateEnum.SIGN_UP_END.getCode());
                isUpdate = true;
            }

            if (event.getEventStart().getTime() < System.currentTimeMillis() && event.getState().equals(EventStateEnum.SIGN_UP_END.getCode())) {
                event.setState(EventStateEnum.EVENT_START.getCode());
                isUpdate = true;
            }
            if (event.getEventEnd().getTime() < System.currentTimeMillis() && event.getState().equals(EventStateEnum.EVENT_START.getCode())) {
                event.setState(EventStateEnum.EVENT_END.getCode());
                isUpdate = true;
            }
            if (isUpdate) {
                updateById(event);
            }
        }
    }

    /**
     * 树形查询所有可报名的赛事
     * @return
     */
    @Override
    public List<TreeVo> listRegistrableAsTree() {
//        查询所有可报名赛事
        List<Event> list = list(new LambdaQueryWrapper<Event>().eq(Event::getState, EventStateEnum.SIGN_UP.getCode()));
        HashMap<String, List<TreeVo>> map = new HashMap<>();
//        根据赛事年度分组
        list.forEach(event -> {
            List<TreeVo> treeVos = map.get(event.getYear() + "");
            if (treeVos == null) {
                ArrayList<TreeVo> values = new ArrayList<>();
                map.put(event.getYear() + "", values);
            }
            List<TreeVo> years = map.get(event.getYear() + "");
            years.add(new TreeVo(event.getEventName(), event.getGuid(), null));
        });
        ArrayList<TreeVo> result = new ArrayList<>();
//        转移数据
        map.entrySet().forEach(entry -> {
            result.add(new TreeVo(entry.getKey() + "年", "", entry.getValue()));
        });
        return result;
    }

    @Override
    public void removeEventBatchByIds(List<String> ids) {
        ids.forEach(id -> {
//            查出来赛事拥有的项目
            List<Program> list = programService.list(new LambdaQueryWrapper<Program>().eq(Program::getEventGuid, id));
//            取出项目的id
            List<String> collect = list.stream().map(Program::getGuid).collect(Collectors.toList());
//            删除项目
            programService.removeBatchByIds(collect);
//            删除赛事
            removeById(id);
        });
    }
}
