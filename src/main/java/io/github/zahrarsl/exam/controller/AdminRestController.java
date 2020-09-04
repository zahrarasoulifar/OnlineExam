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

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
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
