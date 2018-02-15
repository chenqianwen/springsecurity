package com.example.security.rbac.web.controller;

import com.example.security.rbac.dto.AdminCondition;
import com.example.security.rbac.dto.AdminInfo;
import com.example.security.rbac.po.Admin;
import com.example.security.rbac.repository.TestRepository;
import com.example.security.rbac.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    /**
     * 测试查询：
     * 带查询， 多表带条件查询
     * @param condition
     * @param pageable
     * @return
     */
    @GetMapping
    public List<Admin> query(AdminCondition condition, Pageable pageable) {
        Specification specification = new Specification<Admin>() {
            /**
             * @param root  实例是类型化的，且定义了查询的FROM子句中能够出现的类型。
             * @param query 代表一个specific的顶层查询对象，它包含着查询的各个部分，
             *              比如：select 、from、where、group by、order by等
             *              注意：CriteriaQuery对象只对实体类型或嵌入式类型的Criteria查询起作用
             * @param builder   制作查询信息,有很多条件方法，比如制定条件:某条数据的创建日期小于今天。可以有多个查询根。
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                Path username = root.get("username");
                Predicate like = builder.like(username, "%admin%");
                //设定select字段
                query.multiselect(
                    builder.count(id)
                );
                query.where(like);
                return like;
            }
        };
        List all = testRepository.findAll(specification);
        return all;
    }
}
