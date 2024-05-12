package com.htu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htu.entity.Business;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BusinessService extends IService<Business> {
}
