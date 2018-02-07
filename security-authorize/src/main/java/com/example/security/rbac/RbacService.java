package com.example.security.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * role_based access control
 */
public interface RbacService {

    /**
     * 是否有权限
     * @param request
     * @param authentication
     * @return
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
