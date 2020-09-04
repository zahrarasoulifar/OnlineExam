package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.AcademicUserDao;
import io.github.zahrarsl.exam.model.dao.UserDao;
import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserDao userDao;
    private AcademicUserDao academicUserDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setAcademicUserDao(AcademicUserDao academicUserDao) {
        this.academicUserDao = academicUserDao;
    }

    public User getUserByEmail(String email){
        return userDao.findByEmail(email);
    }

    public User getUserById(int id){
        return userDao.findById(id);
    }

    public List<AcademicUser> getVerifiedUsers(){
        return academicUserDao.findByAdminVerificationStatusIsTrue();
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
}
