package com.example;


import com.example.register.ExtensionBootstrap;
import com.example.register.ExtensionRegister;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtensionAutoConfiguration {

    /**
     * 引导类，注册所有的扩展点到容器中
     */
    @Bean(initMethod = "registerExtensionBean")
    @ConditionalOnMissingBean(ExtensionBootstrap.class)
    public ExtensionBootstrap extensionBootstrap() {
        return new ExtensionBootstrap();
    }

    /**
     * 扩展点的本地缓存，缓存了所有的扩展点
     */
    @Bean
    @ConditionalOnMissingBean(ExtensionRepository.class)
    public ExtensionRepository extensionRepository() {
        return new ExtensionRepository();
    }

    /**
     * 扩展点的执行器，需要织入订制逻辑时使用
     */
    @Bean
    @ConditionalOnMissingBean(TenantExtensionExecutor.class)
    public TenantExtensionExecutor tenantExtensionExecutor() {
        return new TenantExtensionExecutor();
    }

    /**
     * 扩展点的注册器，用于注册各个扩展点到本地缓存
     */
    @Bean
    @ConditionalOnMissingBean(ExtensionRegister.class)
    public ExtensionRegister extensionRegister() {
        return new ExtensionRegister();
    }

}
