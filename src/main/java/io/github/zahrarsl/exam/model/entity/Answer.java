package io.github.zahrarsl.exam.model.entity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String content;

    public Answer() {
    }

    public Answer(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
