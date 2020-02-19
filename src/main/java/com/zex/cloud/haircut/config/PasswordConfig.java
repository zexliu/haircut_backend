package com.zex.cloud.haircut.config;

import com.zex.cloud.haircut.util.BCryptPasswordEncoder;
import com.zex.cloud.haircut.util.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordConfig {

    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }
}
