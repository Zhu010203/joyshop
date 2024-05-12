package com.htu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@ServletComponentScan("com.htu.filter")
@EnableCaching
public class OnlinemarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinemarketApplication.class, args);
    }

}
