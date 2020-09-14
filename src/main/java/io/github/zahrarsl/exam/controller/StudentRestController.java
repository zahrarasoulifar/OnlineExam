package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/student")
@PreAuthorize("hasAnyAuthority('ADMIN, STUDENT')")
public class StudentRestController {
    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/studentCourses/{studentId}")
    public List<Course> getStudentCourses(@PathVariable("studentId") int studentId){
        return courseService.getCoursesByStudent(studentId);
    }
}
