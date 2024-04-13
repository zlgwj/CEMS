package com.gwj.cems.runner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwj.cems.pojo.vo.MenuVo;
import com.gwj.cems.service.EventService;
import com.gwj.common.constant.RedisKeyConstant;
import com.gwj.common.utils.RedisUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Resource
    EventService eventService;

    @Resource
    RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        eventService.autoUpdateState();
        String prefix = RedisKeyConstant.MENU_KEY;
        for (int i = 1; i <= 3; i++) {

            InputStream menu1 = getClass().getResourceAsStream("/json/menu_" + i + ".json");
            if (menu1 == null) {
                throw new RuntimeException("菜单数据初始化失败");
            } else {
                List<MenuVo> maps = new ObjectMapper().readValue(menu1, new TypeReference<List<MenuVo>>() {
                });
                redisUtil.set(prefix + i, maps);
            }
        }

        System.out.println("启动成功！！！");
    }
}
