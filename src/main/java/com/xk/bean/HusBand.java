package com.xk.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kai.xu
 * @create 2020-12-29 9:17
 */
@Component
public class HusBand {

    private Wife wife;

    // 只有一个构造器，可以省略
    //@Autowired
    public HusBand(Wife wife){
        this.wife = wife;
        System.out.println("...husband 有参构造...");
    }
}
