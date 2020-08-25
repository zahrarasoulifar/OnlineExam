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
    private AcademicUserDao academicUserDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Autowired
    public void setAcademicUserDao(AcademicUserDao academicUserDao) {
        this.academicUserDao = academicUserDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Admin save(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminDao.save(admin);
    }

    public List<AcademicUser> getUnverifiedUsers(){
       return academicUserDao.findByAdminVerificationStatusIsFalseAndEmailVerificationStatusIsTrue();
    }

    public boolean verifyUser(int userId){
        try{
            academicUserDao.setAdminVerificationStatusTrue(userId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<AcademicUser> getVerifiedUsers(){
        return academicUserDao.findByAdminVerificationStatusIsTrue();
    }
}
