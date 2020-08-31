package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.TeacherDao;
import io.github.zahrarsl.exam.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private TeacherDao teacherDao;

    @Autowired
    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public Teacher getUser(int id) throws Exception {
        try {
            return teacherDao.findById(id);
        } catch (Exception e){
            throw new Exception("finding teacher failed");
        }
    }
}
