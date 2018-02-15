package com.example.security.rbac.repository;

import com.example.security.rbac.po.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 */
@Repository
public interface ResourceRepository extends BaseRepository<Resource,Long> {

    /**
     * 通过name查找资源
     * @param name
     * @return
     */
    @Query("select e from #{#entityName} e where e.name = ?1")
    Resource findByName(String name);

}
