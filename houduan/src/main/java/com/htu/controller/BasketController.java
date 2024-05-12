package com.htu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.htu.common.R;
import com.htu.entity.Basket;
import com.htu.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @GetMapping("/user/{id}")
    public R getByUser(@PathVariable Long id){
        LambdaQueryWrapper<Basket> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Basket::getUserid,id);
        List<Basket> list =basketService.list(lambdaQueryWrapper);
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("购物篮不存在");
    }

    @GetMapping("/goods/{id}")
    public R getByGoods(@PathVariable Long id){
        LambdaQueryWrapper<Basket> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Basket::getGoodsid,id);
        List<Basket> list =basketService.list(lambdaQueryWrapper);
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("购物篮不存在");
    }
    @PostMapping
    public R add(@RequestBody Basket basket){
        boolean save=basketService.save(basket);
        System.out.println(save);
        if (save){
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }

    @PutMapping
    public R addGoods(@RequestBody Basket basket){
        LambdaQueryWrapper<Basket> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(basket.getGoodsid()!=null,Basket::getGoodsid,basket.getGoodsid());
        lambdaQueryWrapper.eq(basket.getUserid()!=null,Basket::getUserid,basket.getUserid());
        boolean update=basketService.update(basket,lambdaQueryWrapper);
        if (update){
            return R.success("成功更改");
        }
        return R.error("添加失败");
    }
    @DeleteMapping("/{id}")
    public R delById(@PathVariable Long id){
        boolean del=basketService.removeById(id);
        if (del){
           return R.success("删除成功");
        }
        return R.error("删除失败");
    }
    @GetMapping("/goods")
    public R delByGoodsId(Long goodsid,Long userid){
        LambdaQueryWrapper<Basket> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Basket::getGoodsid,goodsid);
        lambdaQueryWrapper.eq(Basket::getUserid,userid);
        boolean del=basketService.remove(lambdaQueryWrapper);
        if (del){
           return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    @DeleteMapping("/goods/{id}")
    public R delByGoodsId(@PathVariable Long id){
        LambdaQueryWrapper<Basket> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Basket::getGoodsid,id);
        boolean del=basketService.remove(lambdaQueryWrapper);
        if (del){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }
    @DeleteMapping("/user/{id}")
    public R delByUserId(@PathVariable Long id){
        LambdaQueryWrapper<Basket> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Basket::getUserid,id);
        boolean del=basketService.remove(lambdaQueryWrapper);
        if (del){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
