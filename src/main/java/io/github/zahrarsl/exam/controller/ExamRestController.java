package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.Question;
import io.github.zahrarsl.exam.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/exam")
@PreAuthorize("isAuthenticated()")
public class ExamRestController {

    private ExamService examService;

    @Autowired
    public void setExamService(ExamService examService) {
        this.examService = examService;
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
    public Map<Question, Double> getExamQuestions(@PathVariable("examId") int examId) {
        return examService.get(examId).getQuestions();
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
        return examService.getExamTotalPoint(examId);
    }
}
