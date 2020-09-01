package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.Exam;
import io.github.zahrarsl.exam.service.CourseService;
import io.github.zahrarsl.exam.service.ExamService;
import io.github.zahrarsl.exam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/exam")
@PreAuthorize("isAuthenticated()")
public class ExamController {

    private ExamService examService;
    private CourseService courseService;
    private TeacherService teacherService;

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
    public ModelAndView getEditExamPage(@PathVariable("examId") String examId){
        try {
            Exam exam = examService.get(Integer.parseInt(examId));
            return new ModelAndView("teacher_exam", "exam", exam);
        }catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("authorized_error", "message", e.getMessage());
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
                "exam", examService.get(Integer.parseInt(examId)));
        modelAndView.addObject("edit_message", result);
        return modelAndView;
    }
}
