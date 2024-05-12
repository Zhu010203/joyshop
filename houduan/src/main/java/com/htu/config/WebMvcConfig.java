package com.htu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({"com.htu.controller","com.htu.config"})
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    //允许跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
       //允许所有请求
        registry.addMapping("/**")
                //设置允许的ip
                .allowedOriginPatterns("*")
                //设置允许的方法
                .allowedMethods("GET","POST","PUT","DELETE")
                //设置可以允许凭据
                .allowCredentials(true)
                //设置时间
                .maxAge(3600);
    }
//访问静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置访问的路径
        registry.addResourceHandler("/photo/**").addResourceLocations("file:e:/onlineshopping/onlinemarket/photo/");
    }

    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSizePerFile(1024*1024*10);
        return commonsMultipartResolver;
    }
}
