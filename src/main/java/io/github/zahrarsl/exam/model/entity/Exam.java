package io.github.zahrarsl.exam.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private int time;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endDate;
    private boolean stopped;
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Course course;

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyJoinColumn(name = "key_id")
    @Column(name = "question_point")
    @JsonIgnore
    private Map<Question, Float> questions;

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyJoinColumn(name = "student_id")
    @Column(name = "start_time")
    @JsonIgnore
    private Map<Student, Date> students;

    public Exam(String title, String description, int time, Date startDate, Date endDate) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Exam() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Map<Question, Float> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<Question, Float> questions) {
        this.questions = questions;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Map<Student, Date> getStudents() {
        return students;
    }

    public void setStudents(Map<Student, Date> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "{" +
                "id: " + id +
                ", title: '" + title + '\'' +
                ", description: '" + description + '\'' +
                ", time: " + time +
                ", startDate: " + startDate +
                ", endDate: " + endDate + " }";
    }
}
