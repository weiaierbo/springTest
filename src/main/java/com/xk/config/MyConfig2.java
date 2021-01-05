package com.xk.config;

import com.xk.bean.Person;
import com.xk.bean.Person2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author kai.xu
 * @create 2020-12-28 19:48
 */
@PropertySource(value = {"classpath:/person.properties"})
@Configuration
public class MyConfig2 {

    @Bean
    public Person2 person2(){
        return new Person2("xx",12);
    }
}
