package io.github.zahrarsl.exam.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.model.entity.Exam;
import io.github.zahrarsl.exam.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
