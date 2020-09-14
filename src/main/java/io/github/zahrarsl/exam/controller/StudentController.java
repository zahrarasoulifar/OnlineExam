package io.github.zahrarsl.exam.controller;


import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.model.entity.Student;
import io.github.zahrarsl.exam.model.entity.Teacher;
import io.github.zahrarsl.exam.service.CourseService;
import io.github.zahrarsl.exam.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
@PreAuthorize("hasAnyAuthority('ADMIN, STUDENT')")
public class StudentController {
    private StudentService studentService;
    private CourseService courseService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(value = "/profile/{studentId}", method = RequestMethod.GET)
    public ModelAndView getStudentProfilePage(@PathVariable("studentId") int id){
        try {
            Student user = studentService.getUser(id);
            return new ModelAndView("student_profile", "user", user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("error", "message", e.getMessage());
        }
    }

    @RequestMapping(value = "/home_page/{studentId}")
    public ModelAndView getStudentHome(@PathVariable("studentId") int studentId){
        try {
            return new ModelAndView("student_home", "user",
                    studentService.getUser(studentId));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value = "/course/{studentId}/{courseId}", method = RequestMethod.GET)
    public ModelAndView getStudentCoursePage(@PathVariable("courseId") String courseId,
                                      @PathVariable("studentId") String studentId){
        try {
            Course course = courseService.getCourse(Integer.parseInt(courseId));
            Student student = studentService.getUser(Integer.parseInt(studentId));
            ModelAndView modelAndView = new ModelAndView("student_course");
            modelAndView.addObject("user", student);
            modelAndView.addObject("course", course);
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("error", "message", e.getMessage());
        }
    }

    @GetMapping(value = "/points/{studentId}")
    public ModelAndView getStudentPointsPage(@PathVariable("studentId") int studentId) {
        try {
            Student student = studentService.getUser(studentId);
            return new ModelAndView("student_points", "student", student);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
