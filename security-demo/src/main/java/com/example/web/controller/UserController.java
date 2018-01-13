package com.example.web.controller;

import com.example.dto.User;
import com.example.dto.UserQueryCondition;
import com.example.exception.UserNotFindException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/me")
    public Object getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }


    @GetMapping
    public List<User> find(UserQueryCondition condition, Pageable pageable){
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setUsername ("于静");
        list.add(user);
        return list;
    }
    @GetMapping("/{id}")
    public User findOne(@PathVariable("id") Long userId){
//        throw new RuntimeException("not find");
        System.out.println("进入user");
        User user = new User();
        user.setUsername("于静");
        return user;
    }
//    @GetMapping("/user")
//    public List<User> find(@RequestParam(name = "username",required = false,defaultValue = "tom") String nickname){
//        List<User> list = new ArrayList<>();
//        User user = new User();
//        user.setUsername("于静");
//        list.add(user);
//        return list;
//    }
}
