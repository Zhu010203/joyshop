package com.htu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htu.entity.Message;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MessageService extends IService<Message> {


}
