package com.thinking.controller;

import com.example.BizScenario;
import com.example.TenantExtensionExecutor;
import com.thinking.service.AbstractExampleExtensionExt;
import com.thinking.service.TenantServiceExt;
import com.thinking.starter.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyichao
 * @version 1.0
 * @date 2023/8/11 23:02
 */

@RestController
@RequestMapping("/test")
public class Controller {

    @Autowired
    private User user;

    @Autowired
    private TenantExtensionExecutor extensionExecutor;


    @GetMapping("/hello")
    public String hello(){
        return user.getUsername();
    }




    @GetMapping("/tenant/{tenant}")
    public String tenant(@PathVariable("tenant") String tenant){


        BizScenario bizScenario =  BizScenario.valueOf("biz1", tenant, "scenario1");

        // 查找并执行扩展点
        extensionExecutor.execute(TenantServiceExt.class, bizScenario, ext -> {

            ext.execute(tenant);

            return null; // 如果不需要返回值，可以返回 null 或者其他默认值
        });

        return tenant;
    }

}
