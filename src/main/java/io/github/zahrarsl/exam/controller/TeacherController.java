package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.model.entity.Exam;
import io.github.zahrarsl.exam.model.entity.Teacher;
import io.github.zahrarsl.exam.service.CourseService;
import io.github.zahrarsl.exam.service.ExamService;
import io.github.zahrarsl.exam.service.StudentService;
import io.github.zahrarsl.exam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/teacher")
@PreAuthorize("hasAuthority('TEACHER')")
public class TeacherController {

    private TeacherService teacherService;
    private CourseService courseService;
    private ExamService examService;
    private StudentService studentService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/profile/{teacherId}", method = RequestMethod.GET)
    public ModelAndView getTeacherProfilePage(@PathVariable("teacherId") String id){
        try {
            Teacher user = teacherService.getUser(Integer.parseInt(id));
            return new ModelAndView("teacher_profile", "user", user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("error", "message", e.getMessage());
        }
    }

    @RequestMapping(value = "/course/{teacherId}/{courseId}", method = RequestMethod.GET)
    public ModelAndView getTeacherCoursePage(@PathVariable("courseId") String courseId,
                                      @PathVariable("teacherId") String teacherId){
        try {
            Course course = courseService.getCourse(Integer.parseInt(courseId));
            Teacher teacher = teacherService.getUser(Integer.parseInt(teacherId));
            ModelAndView modelAndView = new ModelAndView("teacher_course");
            modelAndView.addObject("user", teacher);
            modelAndView.addObject("course", course);
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("error", "message", e.getMessage());
        }
    }

    @RequestMapping(value = "/add_exam_page/{teacherId}/{courseId}", method = RequestMethod.GET)
    public ModelAndView getTeacherExamPage(@PathVariable("teacherId") String teacherId,
                                    @PathVariable("courseId") String courseId){
        try {
            ModelAndView modelAndView = new ModelAndView("teacher_addExam", "teacherId", teacherId);
            modelAndView.addObject("courseId", courseId);
            modelAndView.addObject("exam", new Exam());
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("error", "message", e.getMessage());
        }
    }

    @RequestMapping(value = "/home_page/{teacherId}")
    public ModelAndView getTeacherHome(@PathVariable("teacherId") String teacherId){
        try {
            return new ModelAndView("teacher_home", "user",
                    teacherService.getUser(Integer.parseInt(teacherId)));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/answers/{examId}")
    public ModelAndView getExamStudentsPage(@PathVariable("examId") int examId) {
        return new ModelAndView("teacher_answers", "exam", examService.getExamById(examId));
    }

    @RequestMapping(value = "/student_answers/{examId}/{studentId}")
    public ModelAndView getStudentAnswersPage(@PathVariable("examId") int examId, @PathVariable("studentId") int studentId) {
        try {
            ModelAndView modelAndView = new ModelAndView("teacher_student_answers");
            modelAndView.addObject("exam", examService.getExamById(examId));
            modelAndView.addObject("student", studentService.getUser(studentId));
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("error", "message", e.getMessage());
        }

    }
}
