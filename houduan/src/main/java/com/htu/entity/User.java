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
public class User implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)//把指定的类型转化String类型返给前端
    private Long id;//用户id

    private String name;//用户名

    private String username;//用户账户

    private String password;//用户密码

    private String img;//图片地址

    private String phone;//用户手机号

    private String sex;//用户性别

    private String idNumber;//用户身份证

    private String address;//用户地址


    private Integer status;//用户状态
    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)//插入和修改时填充
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)//插入时填充字段
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)//插入和修改时填充
    private Long updateUser;
    @TableLogic
    private Integer del;
}
