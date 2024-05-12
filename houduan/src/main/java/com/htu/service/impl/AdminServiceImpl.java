package com.htu.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htu.entity.Admin;
import com.htu.mapper.AdminMapper;
import com.htu.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
