package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.ExamDao;
import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.model.entity.Exam;
import io.github.zahrarsl.exam.model.entity.Question;
import io.github.zahrarsl.exam.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExamService {
    private ExamDao examDao;
    private TeacherService teacherService;
    private CourseService courseService;
    private StudentService studentService;

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

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void save(Exam exam, int teacherId, int courseId) {
        try {
            exam.setTeacher(teacherService.getUser(teacherId));
            exam.setCourse(courseService.getCourse(courseId));
            examDao.save(exam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Exam getExamById(int examId) {
        return examDao.getById(examId);
    }

    public String edit(int examId, String title, int time, String startDate, String endDate) {
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

    public void stopExam(int examId) {
        Exam exam = examDao.getById(examId);
        exam.setStopped(true);
        examDao.save(exam);
    }

    public void addQuestion(Question question, int examId, Float point) {
        Exam exam = getExamById(examId);
        Map<Question, Float> questions = exam.getQuestions();
        questions.put(question, point);
        examDao.save(exam);
    }

    public double calculateExamTotalPoint(int examId) {
        Exam exam = examDao.getById(examId);
        return exam.getQuestions().values().stream().reduce(Float.parseFloat("0"), Float::sum);
    }

    public List<Exam> getFutureExams(Course course) {
        return examDao.getFutureExamsByCourse(course);
    }

    public List<Exam> getCurrentExams(Course course) {
        return examDao.getCurrentExamsByCourse(course);
    }

    public Question getNextQuestion(int examId, int questionId) {
        Pageable pageable = PageRequest.of(0, 1);
        Exam exam = examDao.getById(examId);
        List<Question> questions = examDao.getNextQuestions(exam, questionId, pageable);
        if (questions.size() != 0) {
            return questions.get(0);
        }
        return null;
    }

    public Question getPreviousQuestion(int examId, int questionId) {
        Pageable pageable = PageRequest.of(0, 1);
        Exam exam = examDao.getById(examId);
        List<Question> questions = examDao.getPreviousQuestions(exam, questionId, pageable);
        if (questions.size() != 0) {
            return questions.get(0);
        }
        return null;
    }

    public long getExamTimeSecondsByStudent(int examId, int studentId) {
        Exam exam = examDao.getById(examId);
        int examTimeSeconds = exam.getTime() * 60;
        Date studentStartTime = examDao.getStudentStartTime(exam, studentId);
        System.out.println("lwlwd: " + studentStartTime);
        if (studentStartTime != null) {
            System.out.println("ajskjhd : " + ((new Date()).getTime() - studentStartTime.getTime()) / 1000);
            return examTimeSeconds - ((new Date()).getTime() - studentStartTime.getTime()) / 1000;
        }
        return examTimeSeconds;
    }

    public void addStudentToExam(int examId, int studentId) {
        try {
            Exam exam = examDao.getById(examId);
            Student student = studentService.getUser(studentId);
            if (!exam.getStudents().containsKey(student)) {
                exam.getStudents().put(student, new Date());
                examDao.save(exam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Float getQuestionPoint(int examId, int questionId) {
        return examDao.getQuestionPoint(examId, questionId);
    }

    public List<Student> getExamStudents(int examId) {
        return new ArrayList<>(examDao.getById(examId).getStudents().keySet());
    }

    public List<Exam> getExamByStudent(int studentId) {
        System.out.println("sfsfsdfs : " + examDao.getExamByStudent(studentId).toString());
        return examDao.getExamByStudent(studentId);
    }
}
