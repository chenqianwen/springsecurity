package com.example.web.controller;

import com.example.dto.User;
import com.example.dto.UserQueryCondition;
import com.example.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private SecurityProperties securityProperties;


    /**
     * 新增数据
     * @param user
     * @param errors
     * @return
     */
    @PostMapping
    public User save(@Valid @RequestBody User user, BindingResult errors){
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(
                    error -> log.info(error.getDefaultMessage())
            );
        }
        log.info(user.toString());
        return user;
    }

    /**
     * 通过ID查询数据
     * @param userId
     * @return
     */
    @GetMapping("/{id:\\d+}")
    public User findOne(@PathVariable(name = "id",required = false) String userId){
//        throw new RuntimeException("not find");
        User user = new User();
        user.setUsername("于静");
        return user;
    }

    /**
     * 通过ID更新数据
     * @param id
     * @return
     */
    @PutMapping("/{id:\\d+}")
    public User update(@PathVariable Long id,@Valid @RequestBody User user, BindingResult errors){
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(
                    error -> log.info(error.getDefaultMessage())
            );
        }
        log.info("更新id是{}的数据："+user.toString(),id);
        return user;
    }

    /**
     * 通过ID删除数据
     * @param id
     * @return
     */
    @DeleteMapping("/{id:\\d+}")
    public User delete(@PathVariable Long id){
        log.info("删除id是{}的数据",id);
        return null;
    }

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
    public List<User> find(UserQueryCondition condition,
                           @PageableDefault(page = 1,size = 10,sort = "id,asc") Pageable pageable){
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setUsername ("于静");
        list.add(user);
        return list;
    }

    @GetMapping("/user")
    public List<User> find(@RequestParam(name = "username",required = false,defaultValue = "tom") String nickname){
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setUsername("于静");
        list.add(user);
        return list;
    }

}
