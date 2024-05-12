package com.htu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htu.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService  extends IService<User> {
}
