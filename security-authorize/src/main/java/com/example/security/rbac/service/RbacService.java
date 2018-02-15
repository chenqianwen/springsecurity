package com.example.security.rbac.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
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