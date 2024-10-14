package com.example.register;

import com.example.Extension;
import com.example.ExtensionPoint;
import com.example.Extensions;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.ServiceLoader;

@Component
public class ExtensionBootstrap implements ApplicationContextAware {

    @Resource
    private ExtensionRegister extensionRegister;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void registerExtensionBean() {
        // 加载通过 SPI 定义的实现类，并注入到 spring 容器中
        ServiceLoader<ExtensionPoint> serviceLoader = ServiceLoader.load(ExtensionPoint.class);
        for (ExtensionPoint next : serviceLoader) {
            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(next.getClass()).getBeanDefinition();
            ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
            beanFactory.registerBeanDefinition(next.getClass().getName(), beanDefinition);
        }

        // 加载 @Extension 的所有实现类
        Map<String, Object> extensionBeans = applicationContext.getBeansWithAnnotation(Extension.class);
        extensionBeans.values().forEach(extension -> {
            ExtensionPoint extensionPoint = (ExtensionPoint) extension;
            extensionPoint.registerBefore(applicationContext);
            extensionRegister.doRegistration(extensionPoint);
        });

        // 加载 @Extensions 的所有实现类
        Map<String, Object> extensionsBeans = applicationContext.getBeansWithAnnotation(Extensions.class);
        extensionsBeans.values().forEach(extension -> {
            ExtensionPoint extensionPoint = (ExtensionPoint) extension;
            extensionPoint.registerBefore(applicationContext);
            extensionRegister.doRegistrationExtensions((ExtensionPoint) extension);
        });
    }
}