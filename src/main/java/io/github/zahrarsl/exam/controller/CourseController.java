package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/course")
@PreAuthorize("hasAuthority('ADMIN')")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView addCourse(@ModelAttribute Course course){
        String resultMessage;
        try {
            courseService.save(course);
            resultMessage = "course added successfully!";
        }catch (Exception e){
            e.printStackTrace();
            resultMessage = "due to some problems, course was not added!";
        }
        ModelAndView modelAndView = new ModelAndView("admin_courses");
        modelAndView.addObject("course", new Course());
        modelAndView.addObject("message", resultMessage);
        return modelAndView;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getCoursesPage(){
        return new ModelAndView("admin_courses", "course", new Course());
    }

    @RequestMapping(value = "/getCoursePage/{courseId}", method = RequestMethod.GET)
    public ModelAndView getCoursePage(@PathVariable("courseId") String id){
        try {
            Course course = courseService.getCourse(Integer.parseInt(id));
            return new ModelAndView("admin_course", "course", course);
        }catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("admin_courses");
        }

    }
}
