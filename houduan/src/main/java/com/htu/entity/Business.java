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
public class Business implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)//把指定的类型转化String类型返给前端
    private Long id;

    private String name;

    private String username;

    private String password;

    private String img;//图片地址

    private String detail;

    private String phone;

    private String address;

    private Integer status;
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
