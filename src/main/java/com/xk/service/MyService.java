package com.xk.service;

import com.xk.dao.MyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kai.xu
 * @create 2020-12-28 18:17
 */
@Service
public class MyService {

    @Autowired(required = false)
    private MyDao myDao;

    public void print(){
        System.out.println(myDao);
    }
}
