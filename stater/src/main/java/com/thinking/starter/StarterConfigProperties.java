package com.thinking.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wangyichao
 * @version 1.0
 * @date 2023/8/12 19:34
 */


@ConfigurationProperties(prefix = "spring.thinking.starter")
public class StarterConfigProperties {


    private String Username;
    public StarterConfigProperties() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public static void main(String[] args) {

        new User();
    }
}
