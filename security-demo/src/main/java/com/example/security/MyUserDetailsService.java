package com.example.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import sun.security.util.Password;

@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService,SocialUserDetailsService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录名："+username);
        return buildUser(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("社交登录名："+userId);
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String username) {

        /**
         * 根据用户名查找用户
         */
        /**
         * 模拟从数据库都出来的密码：
         */
        String password = passwordEncoder.encode("123456");
        log.info("数据库密码是："+password);
//        User user = new User(username,"123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        SocialUser user = new SocialUser(username, password, true, true,
                true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
        return user;
    }
}
