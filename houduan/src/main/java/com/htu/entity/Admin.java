package com.htu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Admin implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)//把指定的类型转化String类型返给前端
    private Long id;//管理员id

    private String name;//管理员姓名

    private String username;//管理员账号

    private String password;//管理员密码

    private String img;//图片地址

    private Integer status;//管理员状态
    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)//插入和修改时填充
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)//插入和修改时填充
    private Long updateUser;
    @TableLogic
    private Integer del;//逻辑删除
}
