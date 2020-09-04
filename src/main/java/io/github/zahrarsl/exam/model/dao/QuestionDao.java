package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.Question;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface QuestionDao extends Repository<Question, Integer> {
    Question getById(int id);
    Question save(Question question);
}
