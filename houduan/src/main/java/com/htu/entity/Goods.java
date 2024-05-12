package com.htu.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class Goods {
    @JsonSerialize(using = ToStringSerializer.class)//把指定的类型转化String类型返给前端
    private Long id;//商品id

    private String name;//商品名称
    @TableField(updateStrategy = FieldStrategy.NOT_NULL) // 设置字段策略为：NOT_NULL
    @JsonSerialize(using = ToStringSerializer.class )
    private Long type;//商品类型id

    private String img;//商品图片地址

    private String detail;//商品详情
    @JsonSerialize(using = ToStringSerializer.class)
    private Long businessid;//商品所属商家id

    private Double price;//商品价格

    private Integer stock;//商品库存

    private Integer status;//商品状态
    @TableLogic
    private  Integer del;//逻辑删除

}
