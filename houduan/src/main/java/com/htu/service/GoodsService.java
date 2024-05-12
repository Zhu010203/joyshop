package com.htu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htu.entity.Goods;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GoodsService extends IService<Goods> {
}
