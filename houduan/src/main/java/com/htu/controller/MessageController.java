package com.htu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.htu.common.R;
import com.htu.entity.Message;
import com.htu.service.MessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    //根据id查询
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id){
        LambdaQueryWrapper<Message> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Message::getId,id);
        Message message=messageService.getOne(lambdaQueryWrapper);
        if (message!=null){
            return R.success(message);
        }
        return R.error("未查到符合id的信息");
    }

    //根据发信人id查询
    @GetMapping("/sender/{id}")
    public R getBySenderid(@PathVariable Long id){
        LambdaQueryWrapper<Message> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Message::getSender,id);
        lambdaQueryWrapper.orderBy(true,true,Message::getCreateTime);
        List<Message> list=messageService.list(lambdaQueryWrapper);
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("没有查到消息");
    }
    //根据收信人id查询
    @GetMapping("/receive/{id}")
    public R getByReceiveid(@PathVariable Long id){
        LambdaQueryWrapper<Message> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Message::getReceive,id);
        lambdaQueryWrapper.orderBy(true,true,Message::getCreateTime);
        List<Message> list=messageService.list(lambdaQueryWrapper);
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("没有查到消息");
    }

    //根据消息对象查询
    @PostMapping ("/message")
    public R getBymessage(@RequestBody Message message){
        LambdaQueryWrapper<Message> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(message.getId()!=null,Message::getId,message.getId());
        lambdaQueryWrapper.eq(message.getSender()!=null,Message::getSender,message.getSender());
        lambdaQueryWrapper.eq(message.getReceive()!=null,Message::getReceive,message.getReceive());
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(message.getMessage()),Message::getMessage,message.getMessage());
        lambdaQueryWrapper.eq(message.getIsnew()!=null,Message::getIsnew,message.getIsnew());
        lambdaQueryWrapper.orderBy(true,true,Message::getCreateTime);
        List<Message> list=messageService.list(lambdaQueryWrapper);
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("没有查到消息");
    }
    //添加信息
    @PostMapping
    public R save(@RequestBody Message message){
        message.setDel(0);
        message.setIsnew(1);
        boolean add=messageService.save(message);
        if (add){
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }
    //更改信息
    @PutMapping
    public R update(@RequestBody Message message){
        LambdaQueryWrapper<Message> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(message.getId()!=null,Message::getId,message.getId());
        lambdaQueryWrapper.eq(message.getSender()!=null,Message::getSender,message.getSender());
        lambdaQueryWrapper.eq(message.getReceive()!=null,Message::getReceive,message.getReceive());
        boolean update=messageService.update(message,lambdaQueryWrapper);
        if (update){
            return R.success("更改成功");
        }
        return R.error("更改失败");
    }
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable Long id){
        boolean del=messageService.removeById(id);
        if (del){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
