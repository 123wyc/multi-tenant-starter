package com.example;

import com.example.register.AbstractExtensionExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TenantExtensionExecutor extends AbstractExtensionExecutor {

    private static final String EXTENSION_NOT_FOUND = "extension_not_found";

    @Autowired
    private ExtensionRepository extensionRepository;

    @Override
    protected <C> C locateComponent(Class<C> targetClz, BizScenario bizScenario) {
        C extension = locateExtension(targetClz, bizScenario);
        log.debug("[Located Extension]: " + extension.getClass().getSimpleName());
        return extension;
    }

    protected <Ext> Ext locateExtension(Class<Ext> targetClz, BizScenario bizScenario) {
        checkNull(bizScenario);

        Ext extension;

        log.debug("BizScenario in locateExtension is : " + bizScenario.getUniqueIdentity());

        // first try with full namespace
        extension = firstTry(targetClz, bizScenario);
        if (extension != null) {
            return extension;
        }

        // second try with default scenario
        extension = secondTry(targetClz, bizScenario);
        if (extension != null) {
            return extension;
        }

        // third try with default use case + default scenario
        extension = defaultTenantTry(targetClz, bizScenario);
        if (extension != null) {
            return extension;
        }

        String errMessage = "Can not find extension with ExtensionPoint: " + targetClz + " BizScenario:" + bizScenario.getUniqueIdentity();
        throw new ExtensionException(EXTENSION_NOT_FOUND, errMessage);
    }

    private <Ext> Ext firstTry(Class<Ext> targetClz, BizScenario bizScenario) {
        log.debug("First trying with " + bizScenario.getUniqueIdentity());
        return locate(targetClz.getName(), bizScenario.getUniqueIdentity());
    }

    private <Ext> Ext secondTry(Class<Ext> targetClz, BizScenario bizScenario) {
        log.debug("Second trying with " + bizScenario.getIdentityWithDefaultScenario());
        return locate(targetClz.getName(), bizScenario.getIdentityWithDefaultScenario());
    }

    private <Ext> Ext defaultTenantTry(Class<Ext> targetClz, BizScenario bizScenario) {
        log.debug("Third trying with " + bizScenario.getIdentityWithDefaultTenant());
        return locate(targetClz.getName(), bizScenario.getIdentityWithDefaultTenant());
    }

    private <Ext> Ext locate(String name, String uniqueIdentity) {
        final Ext ext = (Ext) extensionRepository.getExtensionRepository().get(new ExtensionCoordinate(name, uniqueIdentity));
        return ext;
    }

    private void checkNull(BizScenario bizScenario) {
        if (bizScenario == null) {
            throw new IllegalArgumentException("BizScenario can not be null for extension");
        }
    }

}