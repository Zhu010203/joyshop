package com.htu.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MetaObjectHandler1 implements MetaObjectHandler {
    //增加时从线程取出用户id,获取当时时间
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime",LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
        //metaObject.setValue("createUser", 1735937095678996482L);
        //metaObject.setValue("updateUser", 1735937095678996482L);
    }
    //更新时从线程取出用户id,获取当时时间
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
        //metaObject.setValue("updateUser", 1735937095678996482L);
    }
}
