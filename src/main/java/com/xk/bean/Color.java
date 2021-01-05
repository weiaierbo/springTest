package com.xk.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author kai.xu
 * @create 2020-12-28 18:16
 */
public class Color {

    private String name;

    public Color(){
        System.out.println("color construct");
    }

    public Color(String name){
        this.name = name;
        System.out.println("color 有参 construct");
    }
    @PostConstruct
    public void init(){
        System.out.println(this.name+"..init ..");
    }

    @PreDestroy
    public void destroy(){
        System.out.println(this.name+"..destroy ..");
    }
}
