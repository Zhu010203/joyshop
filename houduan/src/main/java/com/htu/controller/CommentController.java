package com.htu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htu.common.R;
import com.htu.entity.Comment;
import com.htu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //查询分页
    @GetMapping
    public R page(Integer page, Integer pageSize,Long goodsid) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getGoodsid,goodsid);
        lambdaQueryWrapper.orderBy(true,true,Comment::getCreateTime);
        Page<Comment> page1 = new Page(page, pageSize);
        Page<Comment> list = commentService.page(page1,lambdaQueryWrapper);
        if (list.getRecords().size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }

    //查询所有
    @GetMapping("/all")
    public R getAll() {
        List<Comment> list = commentService.list();
        if (list.size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }
    @GetMapping("/goods/{id}")
    public R getByGoods(@PathVariable Long id) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getGoodsid,id);
        List<Comment> list = commentService.list(lambdaQueryWrapper);
        if (list.size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }
    @PostMapping("/find")
    public R getByAll(@RequestBody Comment comment){
        LambdaQueryWrapper<Comment> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(comment.getId()!=null,Comment::getId,comment.getId());
        lambdaQueryWrapper.eq(comment.getGoodsid()!=null,Comment::getGoodsid,comment.getGoodsid());
        lambdaQueryWrapper.orderBy(true,true,Comment::getCreateTime);
        List<Comment> list=commentService.list(lambdaQueryWrapper);
        if(list.size()>0){
            return R.success(list);
        }
        return R.error("暂时没有评论哦");
    }

    @PostMapping
    public R add(@RequestBody Comment comment){
        LocalDateTime dateTime = LocalDateTime.now();
        if(comment.getComment()==null){
            comment.setComment("暂无评论");
        }
        comment.setDel(0);
        comment.setCreateTime(dateTime);
        boolean save=commentService.save(comment);
        if(save){
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }
    @PutMapping
    public R update(@RequestBody Comment comment){
        boolean update=commentService.updateById(comment);
        if(update){
            return R.success("更改成功");
        }
        return R.error("更改失败");
    }
    @DeleteMapping("/{id}")
    public R del(@PathVariable Long id){
        LambdaQueryWrapper<Comment> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getId,id);
        boolean del=commentService.remove(lambdaQueryWrapper);
        if(del){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }
}
