package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.AcademicUserDao;
import io.github.zahrarsl.exam.model.dao.StudentDao;
import io.github.zahrarsl.exam.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private StudentDao studentDao;

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Student getUser(int id) throws Exception {
        try {
            return studentDao.findById(id);
        } catch (Exception e){
            throw new Exception("finding student failed");
        }
    }
}
