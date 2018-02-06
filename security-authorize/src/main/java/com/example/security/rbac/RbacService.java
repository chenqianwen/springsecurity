package com.example.security.rbac;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * created by ygl on 2018/1/28
 * role_based access control
 */
public interface RbacService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
