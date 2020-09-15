package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.Exam;
import io.github.zahrarsl.exam.model.entity.Question;
import io.github.zahrarsl.exam.model.entity.Student;
import io.github.zahrarsl.exam.model.entity.StudentAnswer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface StudentAnswerDao extends CrudRepository<StudentAnswer, Integer> {
    StudentAnswer getByQuestionAndStudentAndExam(Question question, Student student, Exam exam);
    List<StudentAnswer> getByExamAndStudent(Exam exam, Student student);
}
