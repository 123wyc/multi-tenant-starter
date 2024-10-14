package com.example;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Extensions {
    String[] tenant() default BizScenario.DEFAULT_TENANT;

    String[] bizId() default BizScenario.DEFAULT_BIZ_ID;

    String[] scenario() default BizScenario.DEFAULT_SCENARIO;

    Extension[] value() default {};

}