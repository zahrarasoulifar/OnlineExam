package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.Answer;
import io.github.zahrarsl.exam.model.entity.MultipleChoiceQuestion;
import io.github.zahrarsl.exam.model.entity.Question;
import io.github.zahrarsl.exam.service.CourseService;
import io.github.zahrarsl.exam.service.ExamService;
import io.github.zahrarsl.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/question")
@PreAuthorize("isAuthenticated()")
public class QuestionRestController {
    private QuestionService questionService;
    private CourseService courseService;
    private ExamService examService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping(value = "/course_questions/{courseId}")
    public List<Question> getCourseQuestions(@PathVariable("courseId") int courseId){
        return courseService.getCourse(courseId).getQuestionBank();
    }

    @GetMapping(value = "/question_bank/{courseId}")
    public List<Question> getQuestionBankByCategory(@PathVariable("courseId") int courseId) {
        String category = courseService.getCourse(courseId).getCategory();
        return courseService.getQuestionBankByCategory(category);
    }

    @PutMapping(value = "/add_existed_question/{examId}/{questionId}/{point}")
    public String addExistedQuestion(@PathVariable("examId") int examId,
                                     @PathVariable("questionId") int questionId,
                                     @PathVariable("point") float point){
        try {
            Question question = questionService.getQuestion(questionId);
            examService.addQuestion(question, examId, point);
            return "question added successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "adding question failed!";
        }
    }

    @PutMapping(value = "/edit")
    public String editQuestion(@RequestBody Question question) {
        try {
            questionService.update(question);
            return "question updated.";
        } catch (Exception e) {
            e.printStackTrace();
            return "update failed.";
        }
    }

    @GetMapping(value = "/choices/{questionId}")
    public List<Answer> getQuestionChoices(@PathVariable("questionId") int questionId) {
        Question question = questionService.getQuestion(questionId);
        if (question instanceof MultipleChoiceQuestion) {
            return ((MultipleChoiceQuestion) question).getChoices();
        } else {
            return null;
        }
    }
}
