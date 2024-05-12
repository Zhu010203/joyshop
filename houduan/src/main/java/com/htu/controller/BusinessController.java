package com.htu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htu.common.BaseContext;
import com.htu.common.R;
import com.htu.entity.Business;
import com.htu.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/login")
    public R login(HttpServletRequest request, String username, String password){
        //密码加密
        String pass= DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        List<Business> list = businessService.list();
        for (Business business:list) {
            if (Objects.equals(business.getUsername(), username) && Objects.equals(business.getPassword(), pass)) {
                redisTemplate.opsForValue().set("token",business.getId());
                return R.success(business);
            }
        }
        return R.error("登陆失败");
    }
    @GetMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("userid");
        //清理redis中token
        redisTemplate.delete("token");
        return R.success("退出成功");
    }
    //查询分页
    @GetMapping
    public R page(Integer page, Integer pageSize) {
        Page<Business> page1 = new Page(page, pageSize);
        Page<Business> list = businessService.page(page1);
        if (list.getRecords().size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }

    //查询所有
    @GetMapping("/all")
    public R getAll() {
        List<Business> list = businessService.list();
        if (list.size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }

    //通过id查询
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id) {
        Business business = businessService.getById(id);
        if (business != null) {
            return R.success(business);
        }
        return R.error("查询错误");
    }

    //所有get方法
    @PostMapping("/find")
    public R getByOther(@RequestBody Business business) {
        LambdaQueryWrapper<Business> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(business.getId() != null,Business::getId, business.getId());
        lambdaQueryWrapper.like(business.getName() != null,Business::getName, business.getName());
        lambdaQueryWrapper.like(business.getUsername() != null,Business::getUsername, business.getUsername());
        lambdaQueryWrapper.eq(business.getPhone() != null,Business::getPhone, business.getPhone());
        lambdaQueryWrapper.eq(business.getAddress() != null,Business::getAddress, business.getAddress());
        lambdaQueryWrapper.eq(business.getStatus() != null,Business::getStatus, business.getStatus());
        List<Business> list = businessService.list(lambdaQueryWrapper);
        if (list.size() > 0) {
            return R.success(list);
        }
        return R.error("查询失败");
    }

    //增加
    @PostMapping
    public R add(@RequestBody Business business) {
        LambdaQueryWrapper<Business> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (business.getImg()==null){
            business.setImg("/photo/business/null.jpg");
        }
        if (business.getPassword()==null) {
            String pass = DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8));
            business.setPassword(pass);
        }else{
            business.setPassword(DigestUtils.md5DigestAsHex(business.getPassword().getBytes(StandardCharsets.UTF_8)));
        }
        business.setDel(0);
        lambdaQueryWrapper.like(Business::getUsername, business.getUsername());
        List<Business> list = businessService.list(lambdaQueryWrapper);
        if (list.size()==0) {
            boolean save = businessService.save(business);
            if (save) {
                return R.success("保存成功");
            }
            return R.error("保存失败");
        }
        return R.error("保存失败,传入存在空值");
    }
    //更改
    @PutMapping()
    public R update(@RequestBody Business business){
        if(business.getPassword()!=null){
            String pass=DigestUtils.md5DigestAsHex(business.getPassword().getBytes(StandardCharsets.UTF_8));
            business.setPassword(pass);
        }
        boolean update=businessService.updateById(business);
        if (update){
            return R.success("更改成功");
        }
        return R.error("更改失败");
    }
    //逻辑删除
    @DeleteMapping("/{id}")
    public R delect(@PathVariable Long id){
        boolean delect=businessService.removeById(id);
        if (delect){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
