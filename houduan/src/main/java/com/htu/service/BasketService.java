package com.htu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htu.entity.Basket;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BasketService extends IService<Basket> {
}
