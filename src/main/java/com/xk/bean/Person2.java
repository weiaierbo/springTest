package com.xk.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author kai.xu
 * @create 2020-12-28 17:34
 */
public class Person2 {

    @Value("${id}")
    private Integer id;

    private String name;

    private Integer age;

    public Person2(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person2 name:"+this.name+" age:"+this.age+" id:"+this.id;
    }
}
