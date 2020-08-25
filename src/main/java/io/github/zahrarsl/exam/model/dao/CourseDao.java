package io.github.zahrarsl.exam.model.dao;


import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@org.springframework.stereotype.Repository
public interface CourseDao extends Repository<Course, Integer> {
    Course save(Course course);
    Course getById(int id);
    void deleteById(int id);

    List<Course> findAll();
    @Query("select teachers from Course c where c.id=:id")
    List<AcademicUser> getCourseTeachers(@Param("id") int id);

    @Query("select students from Course c where c.id=:id")
    List<AcademicUser> getCourseStudents(@Param("id") int id);
}
