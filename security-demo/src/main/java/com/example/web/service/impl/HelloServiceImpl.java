package com.example.web.service.impl;

import com.example.web.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author： ygl
 * @date： 2018/2/10-16:21
 * @Description：
 */
@Service
public class HelloServiceImpl implements HelloService{

    @Override
    public String greet(String name) {
        return "hello " + name;
    }
}
