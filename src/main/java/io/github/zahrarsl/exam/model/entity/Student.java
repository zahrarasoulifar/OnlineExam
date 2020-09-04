package io.github.zahrarsl.exam.model.entity;

import javax.persistence.Entity;

@Entity
public class Student extends AcademicUser {

    public Student(AcademicUser user) {
        super(user);
        this.emailVerificationStatus = user.isEmailVerified();
        this.adminVerificationStatus = user.hasAdminVerified();
    }

    public Student() {
    }
}