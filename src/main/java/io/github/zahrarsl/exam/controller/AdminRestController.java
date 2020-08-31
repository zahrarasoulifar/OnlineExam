package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.Admin;
import io.github.zahrarsl.exam.service.AcademicUserService;
import io.github.zahrarsl.exam.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminRestController {

    private AdminService adminService;
    private AcademicUserService academicUserService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Autowired
    public void setAcademicUserService(AcademicUserService academicUserService) {
        this.academicUserService = academicUserService;
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "text/html")
    public ResponseEntity saveNewStudent(@RequestBody Admin admin){
        try {
            adminService.save(admin);
            return ResponseEntity.ok()
                    .body("welcome " + admin.getFirstName());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("error " + e.getMessage());
        }
    }

    @RequestMapping(value = "/getUnverifiedUsers", produces = "application/json")
    public List<AcademicUser> getUnverifiedUsers(){
        List<AcademicUser> unverifiedUsers = adminService.getUnverifiedUsers();
        return unverifiedUsers;
    }

    @RequestMapping(value = "/verify")
    public boolean verifyUser(@RequestParam("userId") String userId){
        if (userId != null) {
            return adminService.verifyUser(Integer.parseInt(userId));
        }
        else
            return false;
    }

    @RequestMapping(value = "/getVerifiedUsers", produces = "application/json")
    public List<AcademicUser> getVerifiedUsers(){
        List<AcademicUser> verifiedUsers = adminService.getVerifiedUsers();
        return verifiedUsers;
    }

    @DeleteMapping(value = "/delete/{userId}", produces = "text/html")
    public ResponseEntity deleteStudent(@PathVariable("userId") String id){
        try {
            academicUserService.deleteUser(Integer.parseInt(id));
            return ResponseEntity.ok()
                    .body("user deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("error: " + e.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "text/html")
    public ResponseEntity addAdmin(@RequestBody Admin admin) {
        try {
            adminService.save(admin);
            return ResponseEntity.ok()
                    .body("admin added successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("error: " + e.getMessage());
        }

    }


}
