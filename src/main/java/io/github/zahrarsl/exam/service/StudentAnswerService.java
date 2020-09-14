package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.StudentAnswerDao;
import io.github.zahrarsl.exam.model.entity.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentAnswerService {
    private StudentAnswerDao studentAnswerDao;
    private ExamService examService;
    private StudentService studentService;

    @Autowired
    public void setStudentAnswerDao(StudentAnswerDao studentAnswerDao) {
        this.studentAnswerDao = studentAnswerDao;
    }

    @Autowired
    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public StudentAnswer saveStudentAnswer(StudentAnswer studentAnswer) {
        studentAnswer.setMaxPoint(examService.getQuestionPoint(studentAnswer.getExam().getId(),
                studentAnswer.getQuestion().getId()));
        return studentAnswerDao.save(studentAnswer);
    }

    public StudentAnswer getStudentAnswerByQuestionAndStudent (Question question, Student student, Exam exam) {
        return studentAnswerDao.getByQuestionAndStudentAndExam(question, student, exam);
    }

    public void gradeMultipleChoiceAnswer(MultipleChoiceQuestion question, StudentAnswer studentAnswer, int examId) {
        Answer rightAnswer = question.getRightAnswer();
        if (studentAnswer.getContent().equals(rightAnswer.getContent())) {
            studentAnswer.setPoint(examService.getQuestionPoint(examId, question.getId()));
        }
        else {
            studentAnswer.setPoint(Float.parseFloat("0"));
        }
    }

    public void gradeDescriptiveAnswers(List<StudentAnswer> studentAnswers) {
        studentAnswers.forEach(studentAnswer -> {
            if (studentAnswer.getPoint() > studentAnswer.getMaxPoint()) {
                throw new RuntimeException("answer point is more than max point");
            }
        });
        studentAnswerDao.saveAll(studentAnswers);
    }

    public List<StudentAnswer> getDescriptiveStudentAnswersByExam(int examId, int studentId) {
        try {
            List<StudentAnswer> answers = getAnswerByStudentAndExam(examId, studentId);
            answers.removeIf(studentAnswer -> studentAnswer.getQuestion() instanceof MultipleChoiceQuestion);
            return answers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentAnswer> getAnswerByStudentAndExam(int examId, int studentId) throws Exception{
        return studentAnswerDao.getByExamAndStudent(examService.getExamById(examId), studentService.getUser(studentId));
    }

    public Float calculateAnswersTotalPoints(int examId, int studentId) {
        float sum = 0;
        try {
            for (StudentAnswer studentAnswer : getAnswerByStudentAndExam(examId, studentId)) {
                if (studentAnswer.getPoint() != null)
                    sum += studentAnswer.getPoint();
            }
        } catch (Exception e) {
            throw new RuntimeException("getting student answers points failed.");
        }
        return sum;
    }
}
