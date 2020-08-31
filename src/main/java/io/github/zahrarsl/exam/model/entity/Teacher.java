package io.github.zahrarsl.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher extends AcademicUser {

    public Teacher(AcademicUser user) {
        super(user);
        this.emailVerificationStatus = user.isEmailVerified();
        this.adminVerificationStatus = user.hasAdminVerified();
    }

    public Teacher() {
    }

}
