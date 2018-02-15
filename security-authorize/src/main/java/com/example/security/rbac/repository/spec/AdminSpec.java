package com.example.security.rbac.repository.spec;

import com.example.security.rbac.dto.AdminCondition;
import com.example.security.rbac.po.Admin;
import com.example.security.rbac.repository.support.ImoocSpecification;
import com.example.security.rbac.repository.support.QueryWrapper;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 */
public class AdminSpec extends ImoocSpecification<Admin, AdminCondition> {

    public AdminSpec(AdminCondition condition) {
        super(condition);
    }

    @Override
    protected void addCondition(QueryWrapper<Admin> queryWrapper) {
        addLikeCondition(queryWrapper, "username");
    }

}

