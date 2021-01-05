package com.xk.bean;

/**
 * @author kai.xu
 * @create 2020-12-28 17:34
 */
public class Person {

    private Integer id;

    private String name;

    private Integer age;

    public Person(String name, Integer age) {
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
        return "name:"+this.name+" age:"+this.age+" id:"+this.id;
    }
}
