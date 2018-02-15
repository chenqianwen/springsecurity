package com.example.security.rbac.audit;

import com.example.security.rbac.po.Admin;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 * The generic type T defines of what type the properties annotated with @CreatedBy or @LastModifiedBy have to be.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((Admin)authentication.getPrincipal()).getUsername();
    }
}