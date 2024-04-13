package com.zlgewj.generator;

import cn.hutool.core.util.StrUtil;
import com.github.davidfantasy.mybatisplus.generatorui.GeneratorConfig;
import com.github.davidfantasy.mybatisplus.generatorui.MybatisPlusToolsApplication;
import com.github.davidfantasy.mybatisplus.generatorui.mbp.NameConverter;
import com.google.common.base.Strings;

public class GeberatorUIServer {



    public static void main(String[] args) {
        GeneratorConfig config = GeneratorConfig.builder()
                .jdbcUrl("jdbc:mysql://localhost:3316/CAMPUS_EVENT_MANAGEMENT")
                .userName("root")
                .password("123456")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                //数据库schema，POSTGRE_SQL,ORACLE,DB2类型的数据库需要指定
                .schemaName("CAMPUS_EVENT_MANAGEMENT")
                //如果需要修改各类生成文件的默认命名规则，可自定义一个NameConverter实例，覆盖相应的名称转换方法：
                .nameConverter(new NameConverter() {
                    /**
                     * 自定义Entity类文件的名称规则
                     */
                    @Override
                    public String entityNameConvert(String tableName, String tablePrefix) {
                        if (Strings.isNullOrEmpty(tableName)) {
                            return "";
                        }
                        // 表名不祛除前缀。
                        // tableName = tableName.substring(tableName.indexOf("_") + 1, tableName.length());
                        return StrUtil.upperFirst(StrUtil.toCamelCase(tableName.toLowerCase()));
                    }

                    /**
                     * 自定义Service类文件的名称规则
                     */
                    @Override
                    public String serviceNameConvert(String tableName) {
                        return this.entityNameConvert(tableName,"") + "Service";
                    }

                    /**
                     * 自定义Controller类文件的名称规则
                     */
                    @Override
                    public String controllerNameConvert(String tableName) {
                        return this.entityNameConvert(tableName, "") + "Controller";
                    }
                })
                .basePackage("com.gwj.cems")
                .port(8068)
                .build();
        MybatisPlusToolsApplication.run(config);
        System.out.println("启动成功：  http://localhost:" + config.getPort());
    }

}
