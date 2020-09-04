package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.Admin;
import io.github.zahrarsl.exam.model.entity.User;
import io.github.zahrarsl.exam.service.AcademicUserService;
import io.github.zahrarsl.exam.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

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

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getUsersPage(){
        return new ModelAndView("admin_users", "userForSearch", new AcademicUser());
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ModelAndView getUserPage(@PathVariable("userId") String id){
        AcademicUser user = null;
        try {
            user = academicUserService.getUser(Integer.parseInt(id));
            return new ModelAndView("admin_user", "user", user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("error", "message",
                    "error happened while processing your request ");
        }

    }


    @RequestMapping(value = "/signup_page", method = RequestMethod.GET)
    public ModelAndView getAdminSignupPage(){
        return new ModelAndView("admin_signup" );
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView signup(@ModelAttribute User user,
                               HttpServletRequest request){
        ModelAndView modelAndView;
        String resultMessage = "";
        try {
            if (user != null) {
                if (user.getRole().equals("ADMIN")){
                    String number = request.getParameter("admin_number");
                    Admin admin = new Admin(user);
                    admin.setAdminNumber(number);
                    adminService.save(admin);
                    resultMessage = "admin added successfully!";
                }
                else {
                    AcademicUser academicUser = new AcademicUser(user);
                    academicUser.setEmailVerificationStatus(false);
                    academicUser.setAdminVerificationStatus(true);
                    academicUserService.save(academicUser);
                    resultMessage = "user added successfully!";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMessage = "error happened user was not added!";
        }
        modelAndView = new ModelAndView("admin_signup");
        modelAndView.addObject("add_message", resultMessage);
        return modelAndView;
    }

    @RequestMapping(value = "update/{userId}", method = RequestMethod.POST)
    public ModelAndView edit(@ModelAttribute AcademicUser academicUser,
                             @PathVariable("userId") String userId){
        try {
            int id = Integer.parseInt(userId);
            academicUser.setId(id);
            academicUserService.edit(academicUser);
            return new ModelAndView("admin_user", "user", academicUserService.getUser(id));
        }catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("admin_users");
        }
    }

    @RequestMapping(value = "/home")
    public ModelAndView getAdminHome(){
        Admin admin = new Admin();
        admin.setFirstName("Admin");
        return new ModelAndView("admin_home", "user", admin);
    }

}
