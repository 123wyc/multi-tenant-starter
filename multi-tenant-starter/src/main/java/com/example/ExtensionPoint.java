package com.example;

import org.springframework.context.ApplicationContext;

/**
 * 扩展点表示一块逻辑在不同的业务有不同的实现，使用扩展点做接口申明，然后用Extension（扩展）去实现扩展点。
 */
public interface ExtensionPoint {

    default void registerBefore(ApplicationContext applicationContext) {
    }

}
