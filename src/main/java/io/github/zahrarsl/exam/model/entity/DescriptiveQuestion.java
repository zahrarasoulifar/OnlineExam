package io.github.zahrarsl.exam.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class DescriptiveQuestion extends Question {
    @OneToOne(cascade = CascadeType.ALL)
    private Answer answer;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

}
