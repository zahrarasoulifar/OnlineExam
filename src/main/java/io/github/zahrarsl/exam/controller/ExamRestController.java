package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.model.entity.Exam;
import io.github.zahrarsl.exam.model.entity.Question;
import io.github.zahrarsl.exam.model.entity.Student;
import io.github.zahrarsl.exam.service.CourseService;
import io.github.zahrarsl.exam.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/exam")
@PreAuthorize("isAuthenticated()")
public class ExamRestController {

    private ExamService examService;
    private CourseService courseService;

    @Autowired
    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/next_question/{examId}/{questionId}")
    public Question getNextQuestion(@PathVariable("examId") int examId, @PathVariable("questionId") int questionId) {
        System.out.println("examygjy " + examId);
       return examService.getNextQuestion(examId, questionId);
    }


    @GetMapping(value = "/{examId}")
    public Exam getExamById(@PathVariable("examId") int examId) {
        return examService.getExamById(examId);
    }

    @DeleteMapping(value = "/delete/{examId}")
    public String delete(@PathVariable("examId") String id) {
        try {
            examService.delete(Integer.parseInt(id));
            return "exam deleted successfully!";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    @GetMapping(value = "/questions/{examId}")
    public Map<Question, Float> getExamQuestions(@PathVariable("examId") int examId) {
        return examService.getExamById(examId).getQuestions();
    }

    @PutMapping(value = "/stop/{examId}")
    public String stopExam(@PathVariable("examId") String examId) {
        try {
            examService.stopExam(Integer.parseInt(examId));
            return "exam stopped!";
        } catch (Exception e){
            e.printStackTrace();
            return "stopping exam failed!";
        }
    }

    @GetMapping(value = "/total_point/{examId}")
    public double getExamTotalPoint(@PathVariable("examId") int examId) {
        return examService.calculateExamTotalPoint(examId);
    }

    @GetMapping(value = "/future_exams/{courseId}")
    public List<Exam> getCourseFutureExams(@PathVariable("courseId") int courseId) {
        Course course = courseService.getCourse(courseId);
        return examService.getFutureExams(course);
    }

    @GetMapping(value = "/current_exams/{courseId}")
    public List<Exam> getCourseCurrentExams(@PathVariable("courseId") int courseId) {
        Course course = courseService.getCourse(courseId);
        return examService.getCurrentExams(course);
    }

    @GetMapping(value = "/is_finished/{examId}")
    public boolean isExamFinished(@PathVariable("examId") int examId) {
        Exam exam = examService.getExamById(examId);
        return exam.getEndDate().after(new Date()) || exam.isStopped();
    }

    @GetMapping(value = "/students/{examId}")
    public List<Student> getExamStudents(@PathVariable("examId") int examId) {
        return examService.getExamStudents(examId);
    }

    @GetMapping(value = "/byStudent/{studentId}")
    public List<Exam> getExamByStudent(@PathVariable("studentId") int studentId) {
        return examService.getExamByStudent(studentId);
    }
}
