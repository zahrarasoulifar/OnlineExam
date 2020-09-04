package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.Answer;
import io.github.zahrarsl.exam.model.entity.DescriptiveQuestion;
import io.github.zahrarsl.exam.model.entity.MultipleChoiceQuestion;
import io.github.zahrarsl.exam.service.ExamService;
import io.github.zahrarsl.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping("/question")
@PreAuthorize("isAuthenticated()")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    ExamService examService;

    @RequestMapping(value = "/choose_question_type/{examId}/{courseId}", method = RequestMethod.GET)
    public ModelAndView getChooseTypePage(@PathVariable("examId") String examId,
                                       @PathVariable("courseId") String courseId){
        ModelAndView modelAndView = new ModelAndView("add_question_type");
        modelAndView.addObject("examId", examId);
        modelAndView.addObject("courseId", courseId);
        return modelAndView;
    }

    @RequestMapping(value = "/add_multiple_choice_page/{examId}/{courseId}", method = RequestMethod.GET)
    public ModelAndView getAddMultipleChoicePage(@PathVariable("examId") String examId,
                                                 @PathVariable("courseId") String courseId){
        ModelAndView modelAndView = new ModelAndView("add_multipleChoice_question");
        modelAndView.addObject("examId", examId);
        modelAndView.addObject("courseId", courseId);
        modelAndView.addObject("question", new MultipleChoiceQuestion());
        return modelAndView;
    }

    @RequestMapping(value = "/add_descriptive_page/{examId}/{courseId}", method = RequestMethod.GET)
    public ModelAndView getAddDescriptivePage(@PathVariable("examId") String examId,
                                              @PathVariable("courseId") String courseId){
        ModelAndView modelAndView = new ModelAndView("add_descriptive_question");
        modelAndView.addObject("examId", examId);
        modelAndView.addObject("courseId", courseId);
        modelAndView.addObject("question", new DescriptiveQuestion());
        return modelAndView;
    }

    @PostMapping(value = "/add_descriptive/{examId}/{courseId}")
    public ModelAndView addDescriptive(@PathVariable("examId") int examId,
                                       @PathVariable("courseId") int courseId,
                                       HttpServletRequest request,
                                       @ModelAttribute("question") DescriptiveQuestion question){
        String bankStatus = request.getParameter("bankStatus");
        String answerContent = request.getParameter("answer_content");
        String point = request.getParameter("point");
        question.setAnswer(new Answer(answerContent));
        questionService.saveDescriptiveQuestion(question, bankStatus,
                Double.parseDouble(point), examId, courseId);
        return new ModelAndView("teacher_exam", "exam", examService.get(examId));
    }

    @PostMapping(value = "/add_multipleChoice/{examId}/{courseId}")
    public ModelAndView addMultipleChoice(@PathVariable("examId") int examId,
                                          @PathVariable("courseId") int courseId,
                                          HttpServletRequest request,
                                          @ModelAttribute("question") MultipleChoiceQuestion question){
        String bankStatus = request.getParameter("bankStatus");
        String point = request.getParameter("point");
        String rightChoice = request.getParameter("right_choice").split("\\.")[1];
        int choiceNumber = Integer.parseInt(request.getParameter("choicesNumber"));

        ArrayList<Answer> choices = new ArrayList<>();
        for (int i = 1; i <= choiceNumber; i++) {
            String id = String.valueOf(i);
            String choice = request.getParameter(id);
            Answer answer = new Answer(choice);
            if (choice.equals(rightChoice)) {
                question.setRightAnswer(answer);
            }
            choices.add(answer);
        }
        question.setChoices(choices);
        questionService.saveMultipleChoiceQuestion(question, bankStatus,
                Double.parseDouble(point), examId, courseId);
        return new ModelAndView("teacher_exam", "exam", examService.get(examId));
    }


    @GetMapping(value = "/question_bank_page/{examId}/{courseId}")
    public ModelAndView getQuestionBankPage(@PathVariable("examId") int examId,
                                            @PathVariable("courseId") int courseId) {
        ModelAndView modelAndView = new ModelAndView("question_bank");
        modelAndView.addObject("examId", examId);
        modelAndView.addObject("courseId", courseId);
        return modelAndView;
    }

}
