package com.xk.config;

import com.xk.bean.Color;
import org.springframework.context.annotation.*;

/**
 * @author kai.xu
 * @create 2020-12-29 9:03
 */
@ComponentScan({"com.xk.service","com.xk.dao","com.xk.bean"})
@Configuration
public class MyConfigAutowired {

    @Profile("dev")
    @Bean
    public Color color1(){
        return new Color("红");
    }

    @Profile("test")
    @Bean
    public Color color2(){
        return new Color("绿");
    }

    @Profile("prod")
    @Bean
    public Color color3(){
        return new Color("蓝");
    }

}
