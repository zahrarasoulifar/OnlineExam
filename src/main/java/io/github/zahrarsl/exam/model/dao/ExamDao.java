package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.model.entity.Exam;
import io.github.zahrarsl.exam.model.entity.Question;
import io.github.zahrarsl.exam.model.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Repository
public interface ExamDao extends Repository<Exam, Integer> {
    Exam save(Exam exam);
    Exam getById(int id);

    void deleteById(int id);

    @Query("SELECT exam FROM Exam AS exam WHERE exam.course=:course and exam.startDate > CURRENT_DATE and" +
            " exam.endDate > CURRENT_DATE")
    List<Exam> getFutureExamsByCourse(@Param("course") Course course);

    @Query("SELECT exam FROM Exam AS exam WHERE exam.course=:course and exam.startDate < CURRENT_DATE and" +
            " exam.endDate > CURRENT_DATE")
    List<Exam> getCurrentExamsByCourse(@Param("course") Course course);

    @Query("SELECT key(questions) FROM Exam AS exam join exam.questions as questions WHERE exam=:exam and KEY(questions).id >:id")
    List<Question> getNextQuestions(@Param("exam") Exam exam, @Param("id") int id, Pageable pageable);

    @Query("SELECT key(questions) FROM Exam AS exam join exam.questions as questions WHERE exam=:exam and KEY(questions).id <:id")
    List<Question> getPreviousQuestions(@Param("exam") Exam exam, @Param("id") int id, Pageable pageable);

    @Query("SELECT students FROM Exam AS exam join exam.students as students WHERE exam=:exam and KEY(students).id =:id")
    Date getStudentStartTime(@Param("exam") Exam exam, @Param("id") int id);

    @Query("SELECT questions FROM Exam AS exam join exam.questions as questions WHERE exam.id=:examId and KEY(questions).id =:id")
    Float getQuestionPoint(@Param("examId") int examId, @Param("id") int questionId);

    @Query("SELECT exam FROM Exam AS exam join exam.students as students WHERE KEY(students).id =:id")
    List<Exam> getExamByStudent(@Param("id")int id);
}
