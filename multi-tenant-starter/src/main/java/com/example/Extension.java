package com.example;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
//@Repeatable(Extensions.class)
@Component
public @interface Extension {
    /**
     * 租户标识
     */
    String tenant() default BizScenario.DEFAULT_TENANT;

    /**
     * 业务标识
     */
    String bizId()  default BizScenario.DEFAULT_BIZ_ID;

    /**
     * 具体到某个业务场景的标识
     */
    String scenario() default BizScenario.DEFAULT_SCENARIO;
}