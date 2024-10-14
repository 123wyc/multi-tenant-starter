package com.thinking.listener;


import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author wangyichao
 * @version 1.0
 * @date 2023/8/13 18:08
 */
public class MyPropertiesListener implements  ApplicationListener<ApplicationEnvironmentPreparedEvent> {


    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        System.out.println("这是一个监听器");

        final String username =
                event.getEnvironment().getProperty("spring.thinking.starter.username");

        System.out.println(username);


    }

    public static void main(String[] args) {
        System.out.println(String.format("{\"department\":\"%s\"}","12345"));
    }
}
