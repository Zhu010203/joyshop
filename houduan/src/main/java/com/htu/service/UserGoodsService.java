package com.htu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htu.entity.UserGoods;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserGoodsService extends IService<UserGoods> {
}
