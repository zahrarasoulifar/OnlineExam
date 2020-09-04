package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.Admin;
import io.github.zahrarsl.exam.service.AcademicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/academic")
@PreAuthorize("hasAnyAuthority('ADMIN, TEACHER, STUDENT')")
public class AcademicUserRestController {

    private AcademicUserService academicUserService;

    @Autowired
    public void setAcademicUserService(AcademicUserService academicUserService) {
        this.academicUserService = academicUserService;
    }


    @PostMapping(value = "/search/{offset}/{limit}", consumes = "application/json", produces = "application/json")
    public List<AcademicUser> search(@PathVariable("offset") String offset,
                                     @PathVariable("limit") String limit,
                                     @RequestBody AcademicUser user){
        return academicUserService
               .findMaxMatch(user, Integer.parseInt(offset), Integer.parseInt(limit)).getContent();
    }

    @PostMapping(value = "/addByAdmin", consumes = "application/json", produces = "text/html")
    public String addByAdmin(@RequestBody AcademicUser academicUser) {
        academicUser.setAdminVerificationStatus(true);
        String result;
        try {
            academicUserService.save(academicUser);
            result = "user added successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            result = "error: " + e.getMessage();
        }
        return result;
    }
}
