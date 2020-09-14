package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.MultipleChoiceQuestion;
import io.github.zahrarsl.exam.model.entity.Question;
import io.github.zahrarsl.exam.model.entity.StudentAnswer;
import io.github.zahrarsl.exam.service.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/student_answer")
@PreAuthorize("isAuthenticated()")
public class StudentAnswerRestController {
    private StudentAnswerService studentAnswerService;

    @Autowired
    public void setStudentAnswerService(StudentAnswerService studentAnswerService) {
        this.studentAnswerService = studentAnswerService;
    }

    @GetMapping(value = "/{examId}/{studentId}")
    public List<StudentAnswer> getStudentAnswersByExam(@PathVariable("examId") int examId,
                                                       @PathVariable("studentId") int studentId) {
        return studentAnswerService.getDescriptiveStudentAnswersByExam(examId, studentId);
    }

    @PostMapping(value = "/grade_answers")
    public String gradeAnswers(@RequestBody List<StudentAnswer> studentAnswers) {
        System.out.println("hey: array: " + studentAnswers.toString());
        studentAnswers.forEach(studentAnswer -> System.out.println(studentAnswer.getPoint()));
        try {
            studentAnswerService.gradeDescriptiveAnswers(studentAnswers);
            return "answers graded successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }

    @GetMapping(value = "/total_point/{examId}/{studentId}")
    public Float calculateTotalPoint(@PathVariable("examId") int examId, @PathVariable("studentId") int studentId) {
        return studentAnswerService.calculateAnswersTotalPoints(examId, studentId);
    }
}
