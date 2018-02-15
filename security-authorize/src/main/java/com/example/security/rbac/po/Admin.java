package com.example.security.rbac.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 * 管理员(用户)
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Admin extends BaseModel implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = -3521673552808391992L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户的所有角色
     */
    @JsonIgnore
    @OneToMany(mappedBy = "admin", cascade = CascadeType.REMOVE)
    private Set<RoleAdmin> roles = new HashSet<>();
    /**
     * 用户有权访问的所有url，不持久化到数据库
     */
    @Transient
    private Set<String> urls = new HashSet<>();
    /**
     * 用户有权的所有资源id，不持久化到数据库
     */
    @Transient
    private Set<Long> resourceIds = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    /**
     * @return
     */
    public Set<Long> getAllResourceIds() {
        init(resourceIds);
        forEachResource(resource -> resourceIds.add(resource.getId()));
        return resourceIds;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<RoleAdmin> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleAdmin> roles) {
        this.roles = roles;
    }

    public Set<String> getUrls() {
        init(urls);
        forEachResource(resource -> urls.addAll(resource.getUrls()));
        return urls;
    }

    private void init(Set<?> data){
        if (CollectionUtils.isEmpty(data)) {
            if (data == null) {
                data = new HashSet<>();
            }
        }
    }

    private void forEachResource(Consumer<Resource> consumer) {
        for (RoleAdmin role : roles) {
            for (RoleResource resource : role.getRole().getResources()) {
                consumer.accept(resource.getResource());
            }
        }
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

}
