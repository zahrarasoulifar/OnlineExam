package io.github.zahrarsl.exam.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class MultipleChoiceQuestion extends Question {
    @OneToMany (cascade = CascadeType.ALL)
    private List<Answer> choices;
    @OneToOne
    private Answer rightAnswer;

    public List<Answer> getChoices() {
        return choices;
    }

    public void setChoices(List<Answer> choices) {
        this.choices = choices;
    }

    public Answer getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Answer rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

}
