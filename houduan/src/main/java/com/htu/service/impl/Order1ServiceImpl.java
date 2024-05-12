package com.htu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htu.entity.Order1;
import com.htu.mapper.Order1Mapper;
import com.htu.service.Order1Service;
import org.springframework.stereotype.Service;

@Service
public class Order1ServiceImpl extends ServiceImpl<Order1Mapper, Order1> implements Order1Service {
}
