package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.Exam;
import io.github.zahrarsl.exam.service.CourseService;
import io.github.zahrarsl.exam.service.ExamService;
import io.github.zahrarsl.exam.service.StudentService;
import io.github.zahrarsl.exam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping(value = "/exam")
@PreAuthorize("isAuthenticated()")
public class ExamController {

    private ExamService examService;
    private CourseService courseService;
    private TeacherService teacherService;
    private StudentService studentService;

    @Autowired
    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/{teacherId}/{courseId}", method = RequestMethod.POST)
    public ModelAndView addCourse(@ModelAttribute Exam exam,
                                  @PathVariable String teacherId,
                                  @PathVariable String courseId){
        try {
            examService.save(exam, Integer.parseInt(teacherId), Integer.parseInt(courseId));
            ModelAndView modelAndView = new ModelAndView("teacher_course");
            modelAndView.addObject("course", courseService.getCourse(Integer.parseInt(courseId)));
            modelAndView.addObject("user", teacherService.getUser(Integer.parseInt(teacherId)));
            return modelAndView;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/teacher_edit_page/{examId}")
    public ModelAndView getExamPage(@PathVariable("examId") String examId){
        try {
            Exam exam = examService.getExamById(Integer.parseInt(examId));
            return new ModelAndView("teacher_exam", "exam", exam);
        }catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("error", "message", e.getMessage());
        }
    }

    @RequestMapping(value = "/edit/{examId}", method = RequestMethod.POST)
    public ModelAndView editExam(@PathVariable("examId") String examId,
                                 HttpServletRequest request) {
        String result;
        try {
            String title = request.getParameter("title");
            String time = request.getParameter("time");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            result = examService.edit(Integer.parseInt(examId), title, Integer.parseInt(time), startDate, endDate);
        }catch (Exception e){
            e.printStackTrace();
            result = "inputs are invalid";
        }
        ModelAndView modelAndView = new ModelAndView("teacher_exam",
                "exam", examService.getExamById(Integer.parseInt(examId)));
        modelAndView.addObject("edit_message", result);
        return modelAndView;
    }

    @RequestMapping(value = "/student_exam_page/{examId}/{studentId}")
    public ModelAndView getStudentExamPage(@PathVariable("examId") int examId,
                                           @PathVariable("studentId") int studentId){
        try {
            ModelAndView modelAndView = new ModelAndView("student_exam");
            modelAndView.addObject("exam", examService.getExamById(examId));
            modelAndView.addObject("student", studentService.getUser(studentId));
            return modelAndView;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/start/{examId}/{studentId}")
    public String startExam(@PathVariable("examId") int examId, @PathVariable("studentId") int studentId,
                            HttpServletRequest request) {
        examService.addStudentToExam(examId, studentId);
        long examTime = examService.getExamTimeSecondsByStudent(examId, studentId);
        Exam exam = examService.getExamById(examId);
        if (examTime > 0 && !exam.isStopped() && exam.getEndDate().after(new Date())) {
            request.setAttribute("time", examTime);
            request.setAttribute("examId", examId);
            request.setAttribute("studentId", studentId);
            return "forward:/question/start";
        } else {
            return "exam_finished";
        }
    }

    @GetMapping(value = "/exam_finished")
    public ModelAndView getExamFinishedPage(){
        return new ModelAndView("exam_finished");
    }

}
