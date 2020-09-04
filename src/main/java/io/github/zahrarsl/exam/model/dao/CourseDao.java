package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.model.entity.Student;
import io.github.zahrarsl.exam.model.entity.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@org.springframework.stereotype.Repository
public interface CourseDao extends Repository<Course, Integer> {
    Course save(Course course);
    Course getById(int id);
    List<Course> getByCategory(String category);
    void deleteById(int id);

    List<Course> findAll();
    @Query("select teachers from Course c where c.id=:id")
    List<Teacher> getCourseTeachers(@Param("id") int id);

    @Query("select students from Course c where c.id=:id")
    List<Student> getCourseStudents(@Param("id") int id);

    @Query("SELECT c FROM Course AS c WHERE :teacher MEMBER OF c.teachers")
    List<Course> getByTeacher(@Param("teacher") Teacher teacher);
}
