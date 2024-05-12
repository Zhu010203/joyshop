package com.htu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htu.entity.Admin;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminService extends IService<Admin> {
}
