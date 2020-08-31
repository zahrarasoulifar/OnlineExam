package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.Teacher;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface TeacherDao extends Repository<Teacher, Integer> {
    Teacher save(Teacher teacher);
    Teacher findById(int id);
    List<Teacher> findAll();
}
