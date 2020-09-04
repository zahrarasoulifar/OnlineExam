package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.ExamDao;
import io.github.zahrarsl.exam.model.entity.Exam;
import io.github.zahrarsl.exam.model.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@Service
public class ExamService {
    private ExamDao examDao;
    private TeacherService teacherService;
    private CourseService courseService;

    @Autowired
    public void setExamDao(ExamDao examDao) {
        this.examDao = examDao;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public void save(Exam exam, int teacherId, int courseId){
        try {
            exam.setTeacher(teacherService.getUser(teacherId));
            exam.setCourse(courseService.getCourse(courseId));
            examDao.save(exam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Exam get(int examId) {
        return examDao.getById(examId);
    }

    public String edit(int examId, String title, int time , String startDate, String endDate) {
        Exam exam = examDao.getById(examId);
        String result = "";
        try {
            exam.setStartDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(startDate));
            exam.setEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(endDate));
            exam.setTitle(title);
            exam.setTime(time);
            examDao.save(exam);
            result = "exam edited successfully";
        } catch (ParseException e) {
            e.printStackTrace();
            result = "failed to save exam.";
        }
        return result;
    }

    public void delete(int id) {
        examDao.deleteById(id);
    }

    public void stopExam (int examId){
        Exam exam = examDao.getById(examId);
        exam.setStopped(true);
        examDao.save(exam);
    }

    public void addQuestion(Question question, int examId, double point) {
        Exam exam = get(examId);
        Map<Question, Double> questions = exam.getQuestions();
        questions.put(question, point);
        examDao.save(exam);
    }

    public double getExamTotalPoint(int examId) {
        Exam exam = examDao.getById(examId);
        return exam.getQuestions().values().stream().reduce(0.0, Double::sum);
    }
}
