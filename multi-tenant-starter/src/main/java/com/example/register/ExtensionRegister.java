package com.example.register;

import com.example.*;
import jakarta.annotation.Resource;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

@Component
public class ExtensionRegister {

    @Resource
    private ExtensionRepository extensionRepository;

    public final static String EXTENSION_EXT_NAMING = "Ext";

    public void doRegistration(ExtensionPoint extensionObject) {
        Class<?> extensionClz = extensionObject.getClass();
        if (AopUtils.isAopProxy(extensionObject)) {
            extensionClz = ClassUtils.getUserClass(extensionObject);
        }
        Extension extensionAnn = AnnotationUtils.findAnnotation(extensionClz, Extension.class);
        Assert.notNull(extensionAnn, extensionClz + "缺乏@Extension注解");
        BizScenario bizScenario = BizScenario.valueOf(extensionAnn.bizId(), extensionAnn.tenant(), extensionAnn.scenario());
        ExtensionCoordinate extensionCoordinate = new ExtensionCoordinate(calculateExtensionPoint(extensionClz), bizScenario.getUniqueIdentity());
        ExtensionPoint preVal = extensionRepository.getExtensionRepository().put(extensionCoordinate, extensionObject);
        if (preVal != null) {
            throw new ExtensionException("扩展点定义重复:" + extensionCoordinate);
        }
    }

    public void doRegistrationExtensions(ExtensionPoint extensionObject) {
        Class<?> extensionClz = extensionObject.getClass();
        if (AopUtils.isAopProxy(extensionObject)) {
            extensionClz = ClassUtils.getUserClass(extensionObject);
        }

        Extensions extensionsAnnotation = AnnotationUtils.findAnnotation(extensionClz, Extensions.class);
        Assert.notNull(extensionsAnnotation, extensionClz + "缺乏@Extensions注解");
        Extension[] extensions = extensionsAnnotation.value();
        if (!ObjectUtils.isEmpty(extensions)) {
            for (Extension extensionAnn : extensions) {
                BizScenario bizScenario = BizScenario.valueOf(extensionAnn.bizId(), extensionAnn.tenant(), extensionAnn.scenario());
                ExtensionCoordinate extensionCoordinate = new ExtensionCoordinate(calculateExtensionPoint(extensionClz), bizScenario.getUniqueIdentity());
                ExtensionPoint preVal = extensionRepository.getExtensionRepository().put(extensionCoordinate, extensionObject);
                if (preVal != null) {
                    throw new ExtensionException("扩展点定义重复:" + extensionCoordinate);
                }
            }
        }

        String[] bizIds = extensionsAnnotation.bizId();
        String[] tenant = extensionsAnnotation.tenant();
        String[] scenarios = extensionsAnnotation.scenario();
        for (String bizId : bizIds) {
            for (String ta : tenant) {
                for (String scenario : scenarios) {
                    BizScenario bizScenario = BizScenario.valueOf(bizId, ta, scenario);
                    ExtensionCoordinate extensionCoordinate = new ExtensionCoordinate(calculateExtensionPoint(extensionClz), bizScenario.getUniqueIdentity());
                    ExtensionPoint preVal = extensionRepository.getExtensionRepository().put(extensionCoordinate, extensionObject);
                    if (preVal != null) {
                        throw new ExtensionException("扩展点定义重复:" + extensionCoordinate);
                    }
                }
            }
        }
    }

    private String calculateExtensionPoint(Class<?> targetClz) {
        Class<?>[] interfaces = ClassUtils.getAllInterfacesForClass(targetClz);
        if (interfaces.length == 0) {
            throw new ExtensionException("扩展点不合法：" + targetClz);
        }
        for (Class<?> intf : interfaces) {
            String extensionPoint = intf.getSimpleName();
            if (extensionPoint.endsWith(EXTENSION_EXT_NAMING)) {
                return intf.getName();
            }
        }
        throw new ExtensionException("扩展点接口名称不合法，命名需要以" + EXTENSION_EXT_NAMING + "结尾");
    }
}
