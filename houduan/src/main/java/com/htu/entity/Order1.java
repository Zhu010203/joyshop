package com.htu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order1 {
    @JsonSerialize(using = ToStringSerializer.class)//把指定的类型转化String类型返给前端
    private Long id;//订单id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userid;//用户id

    private Integer pay;//订单是否支付,已支付为1

    private Integer post;//订单是否发货,已发货为1
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

    @TableField(exist = false)//不与数据库映射
    private  UserGoods[] userGoods;

}
