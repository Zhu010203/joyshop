package com.htu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htu.common.BaseContext;
import com.htu.common.R;
import com.htu.entity.Admin;
import com.htu.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/login")
    public R login(HttpServletRequest request,String username, String password){
        //密码加密
        String pass= DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        List<Admin> list = adminService.list();
        for (Admin admin:list) {
            if (Objects.equals(admin.getUsername(), username) && Objects.equals(admin.getPassword(), pass)){
                request.getSession().setAttribute("userid",admin.getId());
                redisTemplate.opsForValue().set("token",admin.getId());
                return R.success(admin);
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
        Page<Admin> page1 = new Page(page, pageSize);
        Page<Admin> list = adminService.page(page1);
        if (list.getRecords().size() > 0) {
            return R.success(list);
        }
        return R.error("查询错误");
    }

    //查询所有
    @GetMapping("/all")
    public R getAll() {
        List<Admin> list = adminService.list();
        if (list.size() > 0) {
            long id = Thread.currentThread().getId();
            System.out.println(id);
            return R.success(list);
        }
        return R.error("查询错误");
    }

    //通过id查询
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id) {
        Admin admin= adminService.getById(id);
        if (admin != null) {
            return R.success(admin);
        }
        return R.error("查询错误");
    }

    //所有get方法
    @PostMapping("/find")
    public R getByOther(@RequestBody Admin admin) {
        LambdaQueryWrapper<Admin> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(admin.getId()!=null,Admin::getId, admin.getId());
        lambdaQueryWrapper1.like(StringUtils.isNotEmpty(admin.getName()),Admin::getName, admin.getName());
        lambdaQueryWrapper1.like(StringUtils.isNotEmpty(admin.getUsername()),Admin::getUsername,admin.getUsername());
        lambdaQueryWrapper1.eq(admin.getStatus()!=null,Admin::getStatus, admin.getStatus());
        List<Admin> list = adminService.list(lambdaQueryWrapper1);
        if (list.size() > 0) {
            return R.success(list);
        }
        return R.error("查询失败");
    }

    //增加
    @PostMapping
    public R add(@RequestBody Admin admin) {
        if (admin.getImg()==null){
            admin.setImg("/photo/admin/guanliyuan.jpg");
        }
        if (admin.getPassword()==null){
            String pass=DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8));
            admin.setPassword(pass);
        }else {
            admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes(StandardCharsets.UTF_8)));
        }
        admin.setDel(0);
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Admin::getName, admin.getName());
        lambdaQueryWrapper.like(Admin::getUsername,admin.getUsername());
        List<Admin> list = adminService.list(lambdaQueryWrapper);
        if (list.size()==0) {
            boolean save = adminService.save(admin);
            if (save) {
                return R.success("保存成功");
            }
            return R.error("保存失败");
        }
        return R.error("保存失败,已经存在相同的用户");
    }
    //更改
    @PutMapping()
    public R update(@RequestBody Admin admin){
        if(admin.getPassword()!=null) {
            String pass = DigestUtils.md5DigestAsHex(admin.getPassword().getBytes(StandardCharsets.UTF_8));
            admin.setPassword(pass);
        }
        boolean update=adminService.updateById(admin);
        if (update){
            return R.success("更改成功");
        }
        return R.error("更改失败");
    }
    //逻辑删除
    @DeleteMapping("/{id}")
    public R delect(@PathVariable Long id){
        boolean delect=adminService.removeById(id);
        if (delect){
            return R.success("删除成功");
        }
            return R.error("删除失败");
    }

}
