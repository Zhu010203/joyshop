package com.htu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htu.entity.Comment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentService extends IService<Comment> {
}
