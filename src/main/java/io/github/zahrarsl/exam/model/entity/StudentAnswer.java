package io.github.zahrarsl.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class StudentAnswer extends Answer {
    @ManyToOne
    private Student student;
    @ManyToOne
    private Exam exam;
    @ManyToOne
    private Question question;
    private Float point;
    private Float maxPoint;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Float getPoint() {
        return point;
    }

    public void setPoint(Float point) {
        this.point = point;
    }

    public Float getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(Float maxPoint) {
        this.maxPoint = maxPoint;
    }

    @Override
    public String toString() {
        return "StudentAnswer{" +
                "content=" + content +
                ", student=" + student.getId() +
                ", exam=" + exam.getId() +
                ", question=" + question.getId() +
                '}';
    }
}
