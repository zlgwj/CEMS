package com.gwj.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gwj.common.enums.DeleteEnum;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //属性名称不是字段名称
        this.setFieldValByName("createdTime", new Date(), metaObject);
        this.setFieldValByName("isDeleted", DeleteEnum.NOT_DELETE.getCode(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedTime", new Date(), metaObject);
    }
}
