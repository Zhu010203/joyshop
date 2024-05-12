package com.htu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htu.common.R;
import com.htu.entity.Order1;
import com.htu.entity.UserGoods;
import com.htu.service.Order1Service;
import com.htu.service.UserGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class Order1controller {

    @Autowired
    private Order1Service orderService;
    @Autowired
    private UserGoodsService userGoodsService;


    //查询分页
    @GetMapping
    public R page(Integer page, Integer pageSize) {
        Page<Order1> page1 = new Page(page, pageSize);
        Page<Order1> list =orderService.page(page1);
        if (list.getRecords().size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }
    //查询所有
    @GetMapping("/all")
    public R getAll() {
        List<Order1> list = orderService.list();

        if (list.size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }

    //通过userid查询
    @GetMapping("/user/{id}")
    public R getByUser(@PathVariable Long id) {
        LambdaQueryWrapper<Order1> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Order1::getUserid,id);
        lambdaQueryWrapper.orderBy(true,false,Order1::getCreateTime);
        List<Order1> list = orderService.list(lambdaQueryWrapper);
        if (list.size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }
    //通过id查询
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id) {
        LambdaQueryWrapper<Order1> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Order1::getId,id);
        Order1 order = orderService.getOne(lambdaQueryWrapper);
        if (order!=null) {
            return R.success(order);
        }
        return R.error("查询错误");
    }
    //所有get方法
    @PostMapping("/find")
    public R getByOther(@RequestBody Order1 order) {
        LambdaQueryWrapper<Order1> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(order.getId()!=null, Order1::getId,order.getId());
        lambdaQueryWrapper.eq(order.getPay()!=null, Order1::getPay,order.getPay());
        lambdaQueryWrapper.eq(order.getPost()!=null, Order1::getPost,order.getPost());
        lambdaQueryWrapper.orderBy(true,false,Order1::getCreateTime);
        List<Order1> list=orderService.list(lambdaQueryWrapper);
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("查询失败");
    }
    @PostMapping("/findug")
    public R getByug(@RequestBody UserGoods userGoods) {
        LambdaQueryWrapper<UserGoods> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(userGoods.getId()!=null,UserGoods::getId,userGoods.getId());
        lambdaQueryWrapper.eq(userGoods.getGoodsid()!=null,UserGoods::getGoodsid,userGoods.getGoodsid());
        lambdaQueryWrapper.eq(userGoods.getOrderid()!=null,UserGoods::getOrderid,userGoods.getOrderid());
        List<UserGoods> list=userGoodsService.list(lambdaQueryWrapper);
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("查询失败");
    }
    //增加order
    @PostMapping()
    public R add(@RequestBody Order1 order){
        UserGoods[] userGoods=order.getUserGoods();
        if (userGoods!=null) {
        order.setDel(0);
        boolean save = orderService.save(order);
            for (UserGoods u:userGoods) {
                u.setOrderid(order.getId());
                userGoodsService.save(u);
            }
            if (save){
                return R.success("保存成功");
            }
        }
        return R.error("保存失败");
    }

    //更改order
    @PutMapping()
    public R updatepost(@RequestBody Order1 order){
        boolean update=orderService.updateById(order);
        if (update){
            return R.success("更改order成功");
        }
        return R.error("更改order失败");
    }

    //更改
    @PutMapping("/usergoods")
    public R update(@RequestBody Order1 order){
        UserGoods[] userGoods=order.getUserGoods();
        boolean update=orderService.updateById(order);
        //删除所有orderid的UserGoods
        LambdaQueryWrapper<UserGoods> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(UserGoods::getOrderid,order.getId());
        userGoodsService.remove(lambdaQueryWrapper1);
        for (UserGoods u:userGoods) {
            userGoodsService.save(u);
        }
        if (update){
            return R.success("更改order成功");
        }
        return R.error("更改order失败");
    }
    //逻辑删除
    @DeleteMapping("/{id}")
    public R delect(@PathVariable Long id){
        LambdaQueryWrapper<Order1> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Order1::getId,id);
        Order1 order=orderService.getOne(lambdaQueryWrapper);
        LambdaQueryWrapper<UserGoods> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(UserGoods::getOrderid,order.getId());
        userGoodsService.remove(lambdaQueryWrapper1);
        boolean delect=orderService.removeById(id);
        if (delect){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }
}
