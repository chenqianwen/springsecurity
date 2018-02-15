package com.example.security.rbac.repository;

import com.example.security.rbac.po.Admin;
import org.springframework.stereotype.Repository;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 */
@Repository
public interface AdminRepository extends BaseRepository<Admin,Long> {

    /**
     * 通过username查询用户
     * @param username
     * @return
     */
    Admin findByUsername(String username);

}
