package com.htu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htu.entity.Basket;
import com.htu.mapper.BasketMapper;
import com.htu.service.BasketService;
import org.springframework.stereotype.Service;

@Service
public class BasketServiceImpl extends ServiceImpl<BasketMapper, Basket> implements BasketService {
}
