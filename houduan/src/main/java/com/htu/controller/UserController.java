package com.htu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htu.common.BaseContext;
import com.htu.common.R;
import com.htu.entity.Basket;
import com.htu.entity.User;
import com.htu.service.BasketService;
import com.htu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/login")
    public R login(HttpServletRequest request,String username, String password){
        //密码加密
        String pass= DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        List<User> list = userService.list();
        for (User user:list) {
            if (Objects.equals(user.getUsername(), username) && Objects.equals(user.getPassword(), pass)){

                return R.success(user);
            }
        }
        return R.error("登陆失败");
    }
    @GetMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("userid");
        return R.success("退出成功");
    }
    //查询分页
    @GetMapping
    public R page(Integer page, Integer pageSize) {
        Page<User> page1 = new Page(page, pageSize);
        Page<User> list = userService.page(page1);
        if (list.getRecords().size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }

    //查询所有
    @GetMapping("/all")
    public R getAll() {
        List<User> list = userService.list();
        if (list.size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }

    //通过id查询
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            return R.success(user);
        }
        return R.error("查询错误");
    }

    //所有查询方法
    @PostMapping("/find")
    public R getByOther(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(user.getId() != null,User::getId, user.getId());
            lambdaQueryWrapper.like(user.getName() != null,User::getName, user.getName());
            lambdaQueryWrapper.like(user.getUsername() != null,User::getUsername, user.getUsername());
            lambdaQueryWrapper.eq(user.getSex() != null,User::getSex, user.getSex());
            lambdaQueryWrapper.eq(user.getPhone() != null,User::getPhone, user.getPhone());
            lambdaQueryWrapper.eq(user.getIdNumber() != null,User::getIdNumber, user.getIdNumber());
            lambdaQueryWrapper.eq(user.getAddress() != null,User::getAddress, user.getAddress());
            lambdaQueryWrapper.eq(user.getStatus() != null,User::getStatus, user.getStatus());
        List<User> list = userService.list(lambdaQueryWrapper);
        if (list.size() > 0) {
            return R.success(list);
        }
        return R.error("查询失败");
    }

    //增加
    @PostMapping
    public R add(@RequestBody User user) {
        if (user.getImg()==null){
            if (Objects.equals(user.getSex(), "女")) {
                user.setImg("/photo/user/user3.png");
            }else {
                user.setImg("/photo/user/user1.png");
            }
        }
        if(user.getPassword()==null){
            String pass=DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8));
            user.setPassword(pass);
        }else {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        }
        user.setDel(0);

            boolean save = userService.save(user);
            if (save) {
                return R.success("保存成功");
            }
            return R.error("保存失败");
        }
    //更改
    @PutMapping()
    public R update(@RequestBody User user){
        if (user.getPassword()!=null) {
            String pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
            user.setPassword(pass);
        }
        boolean update=userService.updateById(user);
        if (update){
            return R.success("更改成功");
        }
        return R.error("更改失败");
    }
    //逻辑删除
    @DeleteMapping("/{id}")
    public R delect(@PathVariable Long id){
        boolean delect=userService.removeById(id);
        if (delect){
            return R.success("删除成功");
        }
            return R.error("删除失败");
    }

}
