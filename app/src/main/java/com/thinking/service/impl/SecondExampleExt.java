package com.thinking.service.impl;

import com.example.Extension;
import com.thinking.service.AbstractExampleExtensionExt;

@Extension(tenant = "tenant2", bizId = "biz1", scenario = "scenario1")
public class SecondExampleExt  extends AbstractExampleExtensionExt  {
    @Override
    public void execute(String data) {
        System.out.println("Executing SecondExampleExtension with data: " + data);
    }
}