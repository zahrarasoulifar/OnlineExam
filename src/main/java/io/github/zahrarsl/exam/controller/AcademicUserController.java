package io.github.zahrarsl.exam.controller;


import io.github.zahrarsl.exam.model.dao.ConfirmationTokenDao;
import io.github.zahrarsl.exam.model.entity.*;
import io.github.zahrarsl.exam.service.AcademicUserService;
import io.github.zahrarsl.exam.service.EmailSenderService;
import io.github.zahrarsl.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/academic")
public class AcademicUserController {
    private AcademicUserService academicUserService;
    private UserService userService;
    private ConfirmationTokenDao confirmationTokenDao;
    private EmailSenderService emailSenderService;

    @Autowired
    public void setAcademicUserService(AcademicUserService academicUserService) {
        this.academicUserService = academicUserService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setConfirmationTokenDao(ConfirmationTokenDao confirmationTokenDao) {
        this.confirmationTokenDao = confirmationTokenDao;
    }

    @Autowired
    public void setEmailSenderService(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @RequestMapping(value = "/signup_page", method = RequestMethod.GET)
    public ModelAndView getSignupPage(Model model) {
        model.addAttribute("academicUser", new AcademicUser());
        return new ModelAndView("signup", "academicUser", new AcademicUser());
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute("academicUser") AcademicUser academicUser,
                         Model model) {
        try {
            academicUser = academicUserService.save(academicUser);
            ConfirmationToken confirmationToken = new ConfirmationToken(academicUser);

            confirmationTokenDao.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(academicUser.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("zahra.rasouli999@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/academic/confirm-account?token=" + confirmationToken.getConfirmationToken());

            try {
                emailSenderService.sendEmail(mailMessage);
                model.addAttribute("email", academicUser.getEmail());
                return "email_sent";
            } catch (Exception e) {
                model.addAttribute("message", "invalid email address. cannot send email.");
                return "error1";
            }
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error1";
        }

    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public ModelAndView getStudentHomePage(@ModelAttribute("academicUser") AcademicUser academicUser) {
        return new ModelAndView("student_home", "student", academicUser);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/teacher", method = RequestMethod.POST)
    public ModelAndView getTeacherHomePage(@ModelAttribute("academicUser") AcademicUser academicUser) {
        return new ModelAndView("teacher_home", "student", academicUser);
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenDao.findByConfirmationToken(confirmationToken);
        String message;
        if (token != null) {
            AcademicUser user = (AcademicUser) userService.getUserByEmail(token.getUser().getEmail());
            user.setEmailVerificationStatus(true);

            academicUserService.saveWithoutPasswordEncoding(user);
            modelAndView.setViewName("accountVerified");
            message = "Congratulation! your account is verified!";
        } else {
            message = "The link is invalid or broken!";
        }

        return new ModelAndView("account_verification", "message", message);
    }
}