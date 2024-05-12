package com.htu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.htu.filter")
public class OnlinemarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinemarketApplication.class, args);
    }

}
