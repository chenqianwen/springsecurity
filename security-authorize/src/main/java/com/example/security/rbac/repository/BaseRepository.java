package com.example.security.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 * @NoRepositoryBean ：Spring Data should not create instances for at runtime.
 */
@NoRepositoryBean
public interface BaseRepository<T,K extends Serializable> extends JpaRepository<T, K>, JpaSpecificationExecutor<T> {

}
