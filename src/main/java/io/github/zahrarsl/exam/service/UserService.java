package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.UserDao;
import io.github.zahrarsl.exam.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserByEmail(String email){
        return userDao.findByEmail(email);
    }

    public User getUserById(int id){
        return userDao.findById(id);
    }
}
