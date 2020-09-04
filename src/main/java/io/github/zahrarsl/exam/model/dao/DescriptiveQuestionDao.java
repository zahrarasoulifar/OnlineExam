package io.github.zahrarsl.exam.model.dao;

import io.github.zahrarsl.exam.model.entity.DescriptiveQuestion;
import org.springframework.data.repository.Repository;

public interface DescriptiveQuestionDao extends Repository<DescriptiveQuestion, Integer> {
    DescriptiveQuestion save(DescriptiveQuestion question);
}
