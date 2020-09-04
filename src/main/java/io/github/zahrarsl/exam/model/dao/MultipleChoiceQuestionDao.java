package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.MultipleChoiceQuestion;
import org.springframework.data.repository.Repository;

public interface MultipleChoiceQuestionDao extends Repository<MultipleChoiceQuestion, Integer> {
    MultipleChoiceQuestion save(MultipleChoiceQuestion question);
}
