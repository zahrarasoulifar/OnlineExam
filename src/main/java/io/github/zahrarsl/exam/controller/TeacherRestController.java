package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.model.entity.Exam;
import io.github.zahrarsl.exam.model.entity.Teacher;
import io.github.zahrarsl.exam.service.CourseService;
import io.github.zahrarsl.exam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/teacher")
@PreAuthorize("hasAuthority('TEACHER')")
public class TeacherRestController {
    private TeacherService teacherService;
    private CourseService courseService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/teacherCourses/{teacherId}")
    public List<Course> getTeacherCourses(@PathVariable("teacherId") String teacherId){
        return courseService.getCoursesByTeacher(Integer.parseInt(teacherId));
    }

//    @GetMapping(value = "/teacher_exams/{teacherId}")
//    public List<Exam> getTeacherExams(){
//        return teacherService.
//    }
}
