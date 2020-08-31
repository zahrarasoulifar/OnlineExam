package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.Student;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface StudentDao extends Repository<Student, Integer> {
    Student save(Student student);
    Student findById(int id);
    List<Student> findAll();
}
