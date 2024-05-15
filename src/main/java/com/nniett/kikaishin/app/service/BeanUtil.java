package com.nniett.kikaishin.app.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import reactor.util.annotation.NonNull;

// below code is not ours. Source: (Abid Anjum) https://www.linkedin.com/pulse/spring-boot-adding-entity-listeners-application-multiple-abid-anjum/
@Service
public class BeanUtil implements ApplicationContextAware
{
    private static ApplicationContext context;

    public static Object getBean(String beanName)
    {
        return  context.getBean(beanName);
    }

    @Override
    @Autowired
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        BeanUtil.context = applicationContext;
    }
}
