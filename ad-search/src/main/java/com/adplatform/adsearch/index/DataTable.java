package com.adplatform.adsearch.index;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;
    private static final Map<Class<?>, Object> dataTableMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
        log.info("DataTable initialized with ApplicationContext");
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    @SuppressWarnings("unchecked")
    public static <T> T bean(String beanName) {
        if (applicationContext == null) {
            throw new IllegalStateException("DataTable not initialized yet");
        }
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T bean(Class<T> clazz) {
        return of(clazz);
    }

    public static <T> T of(Class<T> clazz) {
        if (applicationContext == null) {
            throw new IllegalStateException("DataTable not initialized yet");
        }
        T instance = clazz.cast(dataTableMap.get(clazz));
        if (instance != null) {
            return instance;
        }
        instance = applicationContext.getBean(clazz);
        dataTableMap.put(clazz, instance);
        return instance;
    }
}