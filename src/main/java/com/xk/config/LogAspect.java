package com.xk.config;

/**
 *
 *
 *
 * @author kai.xu
 * @create 2020-12-29 15:49
 */
public class LogAspect {

    public void pointCut(){

    }

    public void start(){
        System.out.println("---start--");
    }

    public void end(){
        System.out.println("---end--");
    }

    public void logReturn(){
        System.out.println("-----return---");
    }

    public void logException(){
        System.out.println("------exception------");
    }

}
