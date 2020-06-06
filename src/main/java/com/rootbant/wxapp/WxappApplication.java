package com.rootbant.wxapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WxappApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxappApplication.class, args);
    }

}
