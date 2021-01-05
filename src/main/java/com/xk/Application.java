package com.xk;

import com.xk.config.MyConfig;
import com.xk.config.MyConfig2;
import com.xk.config.MyConfigAutowired;
import com.xk.dao.MyDao;
import com.xk.service.MyService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author kai.xu
 * @create 2020-12-28 17:32
 */
public class Application {

    public static void main(String[] args){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig2.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        System.out.println(environment.getProperty("os.name"));
        for(String name : definitionNames) {
            System.out.println(name);
        }
        Object bean = applicationContext.getBean("person2");
        System.out.println(bean);
        applicationContext.close();
    }

    public void printBeans(AnnotationConfigApplicationContext applicationContext){
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        System.out.println(environment.getProperty("os.name"));
        for(String name : definitionNames) {
            System.out.println(name);
        }
    }

    @Test
    public void testAutowired(){
        //AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfigAutowired.class);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("dev");
        applicationContext.register(MyConfigAutowired.class);
        applicationContext.refresh();
        /*MyService serviceBean = applicationContext.getBean(MyService.class);
        serviceBean.print();
        MyDao myDao = applicationContext.getBean(MyDao.class);
        System.out.println(myDao);
        printBeans(applicationContext);*/
    }
}
