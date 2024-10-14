package com.thinking.service.impl;

import com.example.Extension;
import com.thinking.service.AbstractExampleExtensionExt;

@Extension(tenant = "tenant1", bizId = "biz1", scenario = "scenario1")
public class FirstExampleExt extends AbstractExampleExtensionExt {
    @Override
    public void execute(String data) {
        System.out.println("Executing FirstExampleExtension with data: " + data);
    }


}