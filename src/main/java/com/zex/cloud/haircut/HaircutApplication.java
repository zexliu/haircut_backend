package com.zex.cloud.haircut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zex.cloud.haircut.mapper")
public class HaircutApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaircutApplication.class, args);
    }

}
