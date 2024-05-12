package com.htu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htu.entity.Type;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TypeService extends IService<Type> {
}
