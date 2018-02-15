package com.example.security.rbac.service.impl;

import com.example.security.rbac.dto.AdminCondition;
import com.example.security.rbac.dto.AdminInfo;
import com.example.security.rbac.po.Admin;
import com.example.security.rbac.po.RoleAdmin;
import com.example.security.rbac.repository.AdminRepository;
import com.example.security.rbac.repository.RoleAdminRepository;
import com.example.security.rbac.repository.RoleRepository;
import com.example.security.rbac.repository.spec.AdminSpec;
import com.example.security.rbac.repository.support.QueryResultConverter;
import com.example.security.rbac.service.AdminService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author： ygl
 * @date： 2018/2/15
 * @Description：
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleAdminRepository roleAdminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AdminInfo create(AdminInfo adminInfo) {

        Admin admin = new Admin();
        BeanUtils.copyProperties(adminInfo, admin);
        admin.setPassword(passwordEncoder.encode("123456"));
        adminRepository.save(admin);
        adminInfo.setId(admin.getId());

        createRoleAdmin(adminInfo, admin);

        return adminInfo;
    }

    @Override
    public AdminInfo update(AdminInfo adminInfo) {

        Admin admin = adminRepository.findOne(adminInfo.getId());
        BeanUtils.copyProperties(adminInfo, admin);

        createRoleAdmin(adminInfo, admin);

        return adminInfo;
    }

    /**
     * 创建角色用户关系数据。
     * @param adminInfo
     * @param admin
     */
    private void createRoleAdmin(AdminInfo adminInfo, Admin admin) {
        if(CollectionUtils.isNotEmpty(admin.getRoles())){
            roleAdminRepository.delete(admin.getRoles());
        }
        RoleAdmin roleAdmin = new RoleAdmin();
        roleAdmin.setRole(roleRepository.getOne(adminInfo.getRoleId()));
        roleAdmin.setAdmin(admin);
        roleAdminRepository.save(roleAdmin);
    }

    @Override
    public void delete(Long id) {
        adminRepository.delete(id);
    }

    @Override
    public AdminInfo getInfo(Long id) {
        Admin admin = adminRepository.findOne(id);
        AdminInfo info = new AdminInfo();
        BeanUtils.copyProperties(admin, info);
        return info;
    }

    @Override
    public Page<AdminInfo> query(AdminCondition condition, Pageable pageable) {
        Page<Admin> admins = adminRepository.findAll(new AdminSpec(condition), pageable);
        return QueryResultConverter.convert(admins, AdminInfo.class, pageable);
    }

}
