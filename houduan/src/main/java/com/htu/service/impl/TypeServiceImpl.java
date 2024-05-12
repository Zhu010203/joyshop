package com.htu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htu.entity.Type;
import com.htu.mapper.TypeMapper;
import com.htu.service.TypeService;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
}
