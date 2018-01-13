package com.example.security.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sun.security.util.Password;

@Component
public class MyUserDetailsService implements UserDetailsService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登录名："+username);
        /**
         * 根据用户名查找用户
         */
        /**
         * 模拟从数据库都出来的密码：
         */
        String password = passwordEncoder.encode("123456");
        logger.info("数据库密码是："+password);
//        User user = new User(username,"123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        User user = new User(username, password, true, true,
                  true, true,
                  AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user;
    }
}
