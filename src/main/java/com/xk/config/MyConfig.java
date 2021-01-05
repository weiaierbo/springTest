package com.xk.config;

import com.xk.bean.Color;
import com.xk.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author kai.xu
 * @create 2020-12-28 17:34
 */
@ComponentScan("com.xk")
//@Configuration
@Import({Color.class,MyImportSelector.class})
public class MyConfig {

   /* @Bean
    public Person person(){
        System.out.println("-----");
        return new Person("xx",12);
    }*/

    @Bean
    public Color color(){
        return new Color();
    }

}
