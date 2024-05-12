package com.htu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htu.entity.UserGoods;
import com.htu.mapper.UserGoodsMapper;
import com.htu.service.UserGoodsService;
import org.springframework.stereotype.Service;

@Service
public class UserGoodsImpl extends ServiceImpl<UserGoodsMapper, UserGoods> implements UserGoodsService {
}
