package io.github.zahrarsl.exam.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    @Column(unique = true)
    private String number;
    private String category;
    @ManyToMany
    private List<AcademicUser> teachers;
    @ManyToMany
    private List<AcademicUser> students;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AcademicUser> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<AcademicUser> teachers) {
        this.teachers = teachers;
    }

    public List<AcademicUser> getStudents() {
        return students;
    }

    public void setStudents(List<AcademicUser> students) {
        this.students = students;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", number='" + number + '\'' +
                ", category='" + category + '\'' +
                ", teachers=" + teachers +
                ", students=" + students +
                '}';
    }
}
