package com.example.register;



import com.example.BizScenario;
import com.example.ExtensionCoordinate;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractExtensionExecutor {

    public <R, T> R execute(Class<T> targetClz, BizScenario bizScenario, Function<T, R> exeFunction) {
        T component = locateComponent(targetClz, bizScenario);
        return exeFunction.apply(component);
    }

    public <R, T> R execute(ExtensionCoordinate extensionCoordinate, Function<T, R> exeFunction) {
        return execute((Class<T>) extensionCoordinate.getExtensionPointClass(), extensionCoordinate.getBizScenario(), exeFunction);
    }

    public <T> void execute(Class<T> targetClz, BizScenario bizScenario, Consumer<T> exeFunction) {
        T component = locateComponent(targetClz, bizScenario);
        exeFunction.accept(component);
    }

    public <T> void execute(ExtensionCoordinate extensionCoordinate, Consumer<T> exeFunction) {
        execute(extensionCoordinate.getExtensionPointClass(), extensionCoordinate.getBizScenario(), exeFunction);
    }

    protected abstract <C> C locateComponent(Class<C> targetClz, BizScenario context);
}

