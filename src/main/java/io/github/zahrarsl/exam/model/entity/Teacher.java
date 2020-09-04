package io.github.zahrarsl.exam.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Teacher extends AcademicUser {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
    @JsonIgnore
    private List<Exam> exams;
    public Teacher(AcademicUser user) {
        super(user);
        this.emailVerificationStatus = user.isEmailVerified();
        this.adminVerificationStatus = user.hasAdminVerified();
    }

    public Teacher() {
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
}
