package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.User;
import io.github.zahrarsl.exam.service.AcademicUserService;
import io.github.zahrarsl.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class LoginController {

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String forwardToLogin() {
        return "forward:login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLogin() {
        return new ModelAndView("login");
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public ModelAndView getUserHomePage(HttpServletRequest request) {
        ModelAndView modelAndView;
        User user = userService.getUserByEmail(request.getParameter("email"));

        if (user != null) {
            try {
                switch (user.getRole()) {
                    case "ADMIN":
                        modelAndView = new ModelAndView("admin_home", "user", user);
                        modelAndView.addObject("pageNumber", 1);
                        break;
                    case "TEACHER":
                        modelAndView = new ModelAndView("teacher_home", "user", user);
                        modelAndView.addObject("user_state", academicUserService.getState(user.getId()));
                        break;
                    case "STUDENT":
                        modelAndView = new ModelAndView("student_home", "user", user);
                        modelAndView.addObject("user_state", academicUserService.getState(user.getId()));
                        break;
                    default:
                        modelAndView = new ModelAndView("error",
                                "message", "user authority is unavailable");
                }
            } catch (Exception e) {
                modelAndView = new ModelAndView("error",
                        "message", e.getMessage());
            }
        } else {
            modelAndView = new ModelAndView("error",
                    "message", "please login.");
        }
        return modelAndView;
    }

    @GetMapping(value = "/loginError")
    public ModelAndView failLogin() {
        return new ModelAndView("login",
                "login_error", "email or password are incorrect!");
    }

}
