package com.example.security.rbac;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

/**
 * created by ygl on 2018/1/28
 */
@Service("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();

            // 读取用户所拥有权限url
            HashSet<String> urls = new HashSet<>();

            for (String url : urls) {
                if (antPathMatcher.match(url,request.getRequestURI())) {
                    hasPermission=true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}
