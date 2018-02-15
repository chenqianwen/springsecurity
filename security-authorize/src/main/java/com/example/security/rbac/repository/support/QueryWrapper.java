package com.example.security.rbac.repository.support;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 * 包装用于构建JPA动态查询时所需的对象
 */
@Data
public class QueryWrapper<T> {

    /**
     * @param root
     *            JPA Root
     * @param cb
     *            JPA CriteriaBuilder
     * @param predicates
     *            JPA Predicate 集合
     */
    public QueryWrapper(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> predicates) {
        this.root = root;
        this.query = query;
        this.cb = cb;
        this.predicates = predicates;
    }

    /**
     * JPA Root
     */
    private Root<T> root;
    /**
     * JPA CriteriaBuilder
     */
    private CriteriaBuilder cb;
    /**
     * JPA Predicate 集合
     */
    private List<Predicate> predicates;
    /**
     * JPA 查询对象
     */
    private CriteriaQuery<?> query;

    /**
     * <pre>
     *
     * <pre>
     * @param predicate
     * @author jojo 2014-8-12 下午3:12:36
     */
    public void addPredicate(Predicate predicate) {
        this.predicates.add(predicate);
    }

}

