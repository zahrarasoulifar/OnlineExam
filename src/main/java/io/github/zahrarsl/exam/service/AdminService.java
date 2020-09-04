package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.AcademicUserDao;
import io.github.zahrarsl.exam.model.dao.AdminDao;
import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private AdminDao adminDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Admin save(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminDao.save(admin);
    }

}
