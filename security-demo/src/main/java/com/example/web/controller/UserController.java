package com.example.web.controller;

import com.example.dto.User;
import com.example.dto.UserQueryCondition;
import com.example.exception.UserNotFindException;
import com.example.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private SecurityProperties securityProperties;

    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request) {
        // 不管注册还是绑定，都会拿到一个用户唯一标识
        String username = user.getUsername();
        providerSignInUtils.doPostSignUp(username,new ServletWebRequest(request));
    }

    @GetMapping("/me")
    public Object getCurrentUser(Authentication user,HttpServletRequest request) throws UnsupportedEncodingException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(authorization, "bearer ");
        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                  .parseClaimsJws(token).getBody();
        Object company = claims.get("company");
        log.info("company----------------"+company);
        return user;
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
//        System.out.println("进入user");
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
