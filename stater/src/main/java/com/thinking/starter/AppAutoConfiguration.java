package com.thinking.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangyichao
 * @version 1.0
 * @date 2023/8/11 22:02
 */

@Configuration
@EnableConfigurationProperties({StarterConfigProperties.class})
public class AppAutoConfiguration {


    @Autowired
    private  StarterConfigProperties properties;

    @Bean
    public User getUser(){

        final User user = new User();
        user.setUsername(properties.getUsername());
        return user;
    }
}
