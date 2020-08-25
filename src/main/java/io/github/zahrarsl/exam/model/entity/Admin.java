package io.github.zahrarsl.exam.model.entity;

import javax.persistence.Entity;

@Entity
public class Admin extends User{
    private String adminNumber;

    public Admin(String firstName, String lastName, String email, String password, String adminNumber) {
        super(firstName, lastName, email, password);
        role = "ADMIN";
        this.adminNumber = adminNumber;
    }

    public Admin() {
        role = "ADMIN";
    }

    public Admin(User user){
        super(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole());
    }

    public String getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(String adminNumber) {
        this.adminNumber = adminNumber;
    }
}
