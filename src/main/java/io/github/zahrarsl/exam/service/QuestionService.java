package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.DescriptiveQuestionDao;
import io.github.zahrarsl.exam.model.dao.MultipleChoiceQuestionDao;
import io.github.zahrarsl.exam.model.dao.QuestionDao;
import io.github.zahrarsl.exam.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class QuestionService {
    private QuestionDao questionDao;
    private DescriptiveQuestionDao descriptiveQuestionDao;
    private MultipleChoiceQuestionDao multipleChoiceQuestionDao;
    private ExamService examService;
    private CourseService courseService;

    @Autowired
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Autowired
    public void setDescriptiveQuestionDao(DescriptiveQuestionDao questionDao) {
        this.descriptiveQuestionDao = questionDao;
    }

    @Autowired
    public void setMultipleChoiceQuestionDao(MultipleChoiceQuestionDao multipleChoiceQuestionDao) {
        this.multipleChoiceQuestionDao = multipleChoiceQuestionDao;
    }

    @Autowired
    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public Question getQuestion(int id) {
        return questionDao.getById(id);
    }

    public void update(Question question) {
        Question savedQuestion = getQuestion(question.getId());
        savedQuestion.setTitle(question.getTitle());
        savedQuestion.setContent(question.getContent());
        questionDao.save(savedQuestion);
    }

    @Transactional
    public void saveDescriptiveQuestion(DescriptiveQuestion question,
                                        String bankStatus, double point,
                                        int examId, int courseId) {
        Course course = courseService.getCourse(courseId);
        question.setCourse(course);

        Exam exam = examService.get(examId);
        question.setTeacher(exam.getTeacher());

        question = descriptiveQuestionDao.save(question);
        saveQuestionProcess(question, bankStatus, point, examId, courseId);
    }

    @Transactional
    public void saveMultipleChoiceQuestion(MultipleChoiceQuestion question, String bankStatus,
                                           double point , int examId, int courseId) {
        Exam exam = examService.get(examId);
        question.setTeacher(exam.getTeacher());

        Course course = courseService.getCourse(courseId);
        question.setCourse(course);

        question = multipleChoiceQuestionDao.save(question);
        saveQuestionProcess(question, bankStatus, point, examId, courseId);
    }

    public void saveQuestionProcess(Question question, String bankStatus,
                             double point , int examId, int courseId) {
        Course course = courseService.getCourse(courseId);
        question.setCourse(course);

        if (bankStatus.equals("YES")){
            course.getQuestionBank().add(question);
            courseService.save(course);
        }
        examService.addQuestion(question, examId, point);
    }
}