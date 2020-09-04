package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.service.AcademicUserService;
import io.github.zahrarsl.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserRestController {
    private UserService userService;
    private AcademicUserService academicUserService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAcademicUserService(AcademicUserService academicUserService) {
        this.academicUserService = academicUserService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/getVerifiedUsers", produces = "application/json")
    public List<AcademicUser> getVerifiedUsers(){
        List<AcademicUser> verifiedUsers = userService.getVerifiedUsers();
        return verifiedUsers;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/delete/{userId}", produces = "text/html")
    public ResponseEntity deleteUser(@PathVariable("userId") String id){
        try {
            academicUserService.deleteUser(Integer.parseInt(id));
            return ResponseEntity.ok()
                    .body("user deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/verify")
    public boolean verifyUser(@RequestParam("userId") String userId){
        if (userId != null) {
            return userService.verifyUser(Integer.parseInt(userId));
        }
        else
            return false;
    }

    @RequestMapping(value = "/getUnverifiedUsers", produces = "application/json")
    public List<AcademicUser> getUnverifiedUsers(){
        List<AcademicUser> unverifiedUsers = userService.getUnverifiedUsers();
        return unverifiedUsers;
    }
}
