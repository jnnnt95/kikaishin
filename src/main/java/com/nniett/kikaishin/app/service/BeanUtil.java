package com.nniett.kikaishin.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    private static ApplicationContext context;

    public BeanUtil() {
        logger.info("BeanUtil initialized.");
    }

    public static Object getBean(String beanName)
    {
        logger.debug("Bean requested.");
        logger.trace("Bean requested. Bean name: {}.", beanName);
        return  context.getBean(beanName);
    }

    @Override
    @Autowired
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        BeanUtil.context = applicationContext;
    }
}
