package com.htu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htu.entity.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
