package com.htu.controller;


import com.htu.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    //图片存放路径
    @Value("E:/onlineshopping/onlinemarket/photo")
    private String BASE_PATH;
    //图片访问路径
    @Value("/photo")
    private String REQUEST_PATH;

    @RequestMapping("/user")
    public R user(MultipartFile file) throws IOException {
        //获取文件名称
        String filename=file.getOriginalFilename();
        //准备存放的路径
        String basePathUrl = BASE_PATH+"/user/"+filename;
        //将文件拷贝到photo
        file.transferTo(new File(basePathUrl));
        //设置访问路径
        String reqPathUrl = REQUEST_PATH+"/user/"+filename;

        return R.success(reqPathUrl);
    }
    @RequestMapping("/business")
    public R bussiness(MultipartFile file) throws IOException {
        //获取文件名称
        String filename=file.getOriginalFilename();
        //准备存放的路径
        String basePathUrl = BASE_PATH+"/business/"+filename;

        //将文件拷贝到photo
        file.transferTo(new File(basePathUrl));
        //设置访问路径
        String reqPathUrl = REQUEST_PATH+"/business/"+filename;

        return R.success(reqPathUrl);
    }
    @RequestMapping("/admin")
    public R admin(MultipartFile file) throws IOException {
        //获取文件名称
        String filename=file.getOriginalFilename();
        //准备存放的路径
        String basePathUrl = BASE_PATH+"/admin/"+filename;

        //将文件拷贝到photo
        file.transferTo(new File(basePathUrl));
        //设置访问路径
        String reqPathUrl = REQUEST_PATH+"/admin/"+filename;

        return R.success(reqPathUrl);
    }
    @RequestMapping("/goods")
    public R goods(MultipartFile file) throws IOException {
        //获取文件名称
        String filename=file.getOriginalFilename();
        //准备存放的路径
        String basePathUrl = BASE_PATH+"/goods/"+filename;

        //将文件拷贝到photo
        file.transferTo(new File(basePathUrl));
        //设置访问路径
        String reqPathUrl = REQUEST_PATH+"/goods/"+filename;

        return R.success(reqPathUrl);
    }
}
