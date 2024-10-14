package com.example;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExtensionRepository {

    private Map<ExtensionCoordinate, ExtensionPoint> extensionRepository = new HashMap<>();

    public Map<ExtensionCoordinate, ExtensionPoint> getExtensionRepository() {
        return extensionRepository;
    }
}