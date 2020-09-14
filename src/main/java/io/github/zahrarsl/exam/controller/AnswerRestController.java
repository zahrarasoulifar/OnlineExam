package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.MultipleChoiceQuestion;
import io.github.zahrarsl.exam.model.entity.Question;
import io.github.zahrarsl.exam.model.entity.StudentAnswer;
import io.github.zahrarsl.exam.service.ExamService;
import io.github.zahrarsl.exam.service.QuestionService;
import io.github.zahrarsl.exam.service.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/answer")
@PreAuthorize("isAuthenticated()")
public class AnswerRestController {
    private StudentAnswerService studentAnswerService;
    private QuestionService questionService;


    @Autowired
    public void setStudentAnswerService(StudentAnswerService studentAnswerService) {
        this.studentAnswerService = studentAnswerService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping(value = "/save_student_answer")
    public String saveStudentAnswer(@RequestBody StudentAnswer studentAnswer) {
        Question question = questionService.getQuestion(studentAnswer.getQuestion().getId());
        if (question instanceof MultipleChoiceQuestion) {
            studentAnswerService.gradeMultipleChoiceAnswer((MultipleChoiceQuestion) question, studentAnswer,
                    studentAnswer.getExam().getId());
        }
        studentAnswerService.saveStudentAnswer(studentAnswer);
        return "Answer submitted.";
    }
}
