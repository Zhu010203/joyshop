package com.htu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htu.common.R;
import com.htu.entity.*;
import com.htu.service.BasketService;
import com.htu.service.GoodsService;
import com.htu.service.TypeService;
import com.htu.service.UserGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")

public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserGoodsService userGoodsService;

    @Autowired
    private BasketService basketService;


    //查询分页
    @GetMapping
    public R page(Integer page, Integer pageSize) {
        Page<Goods> page1 = new Page(page, pageSize);
        Page<Goods> list = goodsService.page(page1);
        if (list.getRecords().size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }

    //查询所有
    @GetMapping("/all")
    public R getAll() {
        List<Goods> list = goodsService.list();
        if (list.size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }
    //查询所有type
    @GetMapping("/type")
    public R getType(Integer page, Integer pageSize){
        Page<Type> page1 = new Page(page, pageSize);
        Page<Type> list = typeService.page(page1);
        if (list.getRecords().size()>0){
            return R.success(list);
        }
        return R.error("查询type名称失败");
    }
    @GetMapping("/type/all")
    public R getType(){
        List<Type> list = typeService.list();
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("查询type名称失败");
    }
    //根据typeId(类型)查询返回类型的name
    @GetMapping("/type/{type}")
    public R getTypeById(@PathVariable Long type){
        LambdaQueryWrapper<Type> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Type::getId,type);
        Type type1=typeService.getOne(lambdaQueryWrapper1);
        if (type1!=null){
            return R.success(type1.getName());
        }
        return R.error("查询type名称失败");
    }

    //根据businessid(商家id)查询
    @GetMapping("/business/{id}")
    public R getByBusinessId(@PathVariable Long id){
        LambdaQueryWrapper<Goods> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Goods::getBusinessid,id);
        lambdaQueryWrapper.orderBy(true,false, Goods::getStatus,Goods::getId);
        List<Goods> list=goodsService.list(lambdaQueryWrapper);
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("查询失败");
    }
    //根据id查询
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id){
        LambdaQueryWrapper<Goods> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Goods::getId,id);
        Goods goods=goodsService.getOne(lambdaQueryWrapper);
        if (goods!=null){
            return R.success(goods);
        }
        return R.error("查询失败");
    }

    //查询价格位于low与high之间类型为type 商家id为bid的商品
    @PostMapping("/price")
    public R getByPrice(@RequestBody Price price){
        LambdaQueryWrapper<Goods> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(price.getType()!=null,Goods::getType,price.getType());
        lambdaQueryWrapper.ge(price.getLow()!=null,Goods::getPrice,price.getLow());
        lambdaQueryWrapper.le(price.getHigh()!=null,Goods::getPrice,price.getHigh());
        lambdaQueryWrapper.eq(price.getBid()!=null,Goods::getBusinessid,price.getBid());
        List<Goods> list=goodsService.list(lambdaQueryWrapper);
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("查询失败");
    }

    //所有get方法
    @PostMapping("/find")
    public R getByOther(@RequestBody Goods goods) {
        LambdaQueryWrapper<Goods> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(goods.getId()!=null,Goods::getId,goods.getId());
        lambdaQueryWrapper.like(goods.getName()!=null,Goods::getName,goods.getName());
        lambdaQueryWrapper.eq(goods.getBusinessid()!=null,Goods::getBusinessid,goods.getBusinessid());
        lambdaQueryWrapper.eq(goods.getType()!=null,Goods::getType,goods.getType());
        //价格小于等于传入价格
        lambdaQueryWrapper.le(goods.getPrice()!=null,Goods::getPrice,goods.getPrice());
        lambdaQueryWrapper.eq(goods.getStatus()!=null,Goods::getStatus,goods.getStatus());
        List<Goods> list=goodsService.list(lambdaQueryWrapper);
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("查询失败");
    }
    @PostMapping("/find/type")
    public R getType(@RequestBody Type type){
        LambdaQueryWrapper<Type> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(type.getId()!=null,Type::getId,type.getId());
        lambdaQueryWrapper.like(type.getName()!=null,Type::getName,type.getName());
        List<Type> list=typeService.list(lambdaQueryWrapper);
        if (list.size()>0){
            return R.success(list);
        }
        return R.error("查询失败");
    }
    //增加
    @PostMapping
    public R add(@RequestBody Goods goods) {
        LambdaQueryWrapper<Goods> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if (goods.getDetail()==null){
            goods.setDetail("无");
        }
        if (goods.getImg()==null){
            goods.setImg("/photo/goods/null.jpg");
        }
        goods.setDel(0);
        //查询与传入商品name,businessId,type都相同的商品,如果存在认为重复
        lambdaQueryWrapper.like(Goods::getName,goods.getName());
        lambdaQueryWrapper.eq(Goods::getBusinessid,goods.getBusinessid());
        lambdaQueryWrapper.eq(Goods::getType,goods.getType());
        List<Goods> list=goodsService.list(lambdaQueryWrapper);
        if (list.size() == 0) {
            boolean save = goodsService.save(goods);
            if (save){
                return R.success("保存成功");
            }
        }
        return R.error("保存失败,已经存在相同商品");
    }

    @PostMapping("/type")
    public R addType(@RequestBody Type type){
        if (type.getImg()==null){
            type.setImg("/photo/type/null.jpg");
        }
        type.setDel(0);
        LambdaQueryWrapper<Type> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Type::getName,type.getName());
        Type type1=typeService.getOne(lambdaQueryWrapper1);
        if (type1==null){
            boolean save = typeService.save(type);
            if (save){
                return R.success("保存成功");
            }
        }
        return R.error("保存失败,已经存在相同类型");
    }
    //更改
    @PutMapping()
    public R update(@RequestBody Goods goods){
        boolean update=goodsService.updateById(goods);
        if (update){
            return R.success("更改成功");
        }
        return R.error("更改失败");
    }
    //更改
    @PutMapping("/status")
    public R updatestatus(@RequestBody Goods goods){
        LambdaQueryWrapper<Goods> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Goods::getBusinessid,goods.getBusinessid());
        boolean update=goodsService.update(goods,lambdaQueryWrapper);
        if (update){
            return R.success("更改成功");
        }
        return R.error("更改失败");
    }
    @PutMapping("/type")
    public R updateType(@RequestBody Type type){
        boolean update=typeService.updateById(type);
        if (update){
            return R.success("更改type成功");
        }
        return R.error("更改type失败");
    }

    //逻辑删除
    @DeleteMapping("/{id}")
    public R delect(@PathVariable Long id){
        boolean delect=goodsService.removeById(id);
        LambdaQueryWrapper<UserGoods> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(UserGoods::getGoodsid,id);
        userGoodsService.remove(lambdaQueryWrapper1);
        LambdaQueryWrapper<Basket> lambdaQueryWrapper2=new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(Basket::getGoodsid,id);
        basketService.remove(lambdaQueryWrapper2);
        if (delect){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }
    //逻辑删除
    @DeleteMapping("/type/{id}")
    public R delectType(@PathVariable Long id){
        LambdaQueryWrapper<Goods> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Goods::getType,id);
        List<Goods> list=goodsService.list(lambdaQueryWrapper);
        boolean delect=typeService.removeById(id);
        if (delect){
            for (Goods good:list) {
                good.setType(null);
                goodsService.updateById(good);
            }
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }
}
