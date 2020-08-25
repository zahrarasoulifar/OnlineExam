package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.AcademicUserDao;
import io.github.zahrarsl.exam.model.dao.AcademicUserSpecifications;
import io.github.zahrarsl.exam.model.entity.AcademicUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicUserService {
    private AcademicUserDao academicUserDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Autowired
    public void setAcademicUserDao(AcademicUserDao academicUserDao) {
        this.academicUserDao = academicUserDao;
    }

    public AcademicUser save(AcademicUser user) throws Exception{
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return academicUserDao.save(user);
        } catch (Exception e) {
            throw new Exception("this user have signed up before.");
        }

    }

    public AcademicUser saveWithoutPasswordEncoding(AcademicUser user) {
        return academicUserDao.save(user);
    }

    public AcademicUser getUser(int id) throws Exception {
        try {
            return academicUserDao.findById(id);
        } catch (Exception e){
            throw new Exception("finding user failed");
        }
    }

    public void deleteUser(int id) throws Exception {
        try {
            academicUserDao.deleteById(id);
        }catch (Exception e){
           throw new Exception("user was not deleted successfully!");
        }
    }

    public void edit(AcademicUser editedUser) throws Exception {
        AcademicUser user = getUser(editedUser.getId());
        if (!editedUser.getFirstName().equals("")){
            user.setFirstName(editedUser.getFirstName());
        }
        if (!editedUser.getLastName().equals("")){
            user.setLastName(editedUser.getLastName());
        }
        if (!editedUser.getEmail().equals("")){
            user.setEmail(editedUser.getEmail());
        }
        if (!editedUser.getRole().equals("")){
            user.setRole(editedUser.getRole());
        }
        if (!editedUser.getFirstName().equals("")){
            user.setFirstName(editedUser.getFirstName());
        }
        try {
            academicUserDao.save(user);
        }catch (Exception e){
            throw new Exception("user update failed.");
        }
    }


    public Page<AcademicUser> findMaxMatch(AcademicUser user,
                                           int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        return academicUserDao.findAll(AcademicUserSpecifications.findMaxMatch(user), pageable);
    }


    public List<AcademicUser> getTeachers(){
        return academicUserDao.findByRoleEquals("TEACHER");
    }

    public List<AcademicUser> getStudents(){
        return academicUserDao.findByRoleEquals("STUDENT");
    }

    public String getState(int userId) throws Exception{
        AcademicUser user = getUser(userId);
        if (user.hasAdminVerified()) {
            return "verified";
        }
        if (!user.isEmailVerified()) {
            return "waiting for email verification";
        }
        if (user.isEmailVerified() && !user.hasAdminVerified()){
            return "waiting for admin verification";
        }
        return "";
    }
}
