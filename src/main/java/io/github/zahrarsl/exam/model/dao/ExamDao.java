package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.Exam;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface ExamDao extends Repository<Exam, Integer> {
    Exam save(Exam exam);
    Exam getById(int id);

    void deleteById(int id);
}
