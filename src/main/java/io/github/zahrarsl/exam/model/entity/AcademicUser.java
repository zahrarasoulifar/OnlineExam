package io.github.zahrarsl.exam.model.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AcademicUser extends User {
    boolean emailVerificationStatus;
    boolean adminVerificationStatus;

    public AcademicUser(String firstName, String lastName,
                        String email, String password, boolean emailVerificationStatus,
                        boolean adminVerificationStatus, String role) {
        super(firstName, lastName, email, password);
        this.emailVerificationStatus = emailVerificationStatus;
        this.adminVerificationStatus = adminVerificationStatus;
        this.role = role;
    }

    public AcademicUser() {
    }

    public AcademicUser(User user){
        super(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole());
    }

    public boolean isEmailVerified() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    public boolean hasAdminVerified() {
        return adminVerificationStatus;
    }

    public void setAdminVerificationStatus(boolean adminVerificationStatus) {
        this.adminVerificationStatus = adminVerificationStatus;
    }
}
