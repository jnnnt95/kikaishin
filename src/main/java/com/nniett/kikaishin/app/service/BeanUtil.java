package com.nniett.kikaishin.app.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

// below code is not ours. Source: (Abid Anjum) https://www.linkedin.com/pulse/spring-boot-adding-entity-listeners-application-multiple-abid-anjum/
@Service
public class BeanUtil implements ApplicationContextAware
{
    private static ApplicationContext context;
    public static <T> T getBean(Class<T> beanClass)
    {
        return context.getBean(beanClass);
    }
    public static Object getBean(String beanName)
    {
        return  context.getBean(beanName);
    }

    @Override
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
