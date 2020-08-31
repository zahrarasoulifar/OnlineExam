package io.github.zahrarsl.exam.controller;

import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.model.entity.Student;
import io.github.zahrarsl.exam.model.entity.Teacher;
import io.github.zahrarsl.exam.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/course")
@PreAuthorize("hasAuthority('ADMIN')")
public class CourseRestController {

    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(value = "/getCourses", produces = "application/json")
    public List<Course> getCourses() {
        return courseService.getAll();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity saveNewCourse(@RequestBody Course course) {
        try {
            courseService.save(course);
            return ResponseEntity.ok()
                    .body("course saved with title:" + course.getTitle());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("error " + e.getMessage());
        }
    }


    @GetMapping(value = "/{courseId}")
    public Course getCourse(@PathVariable("courseId") String id) {
        return courseService.getCourse(Integer.parseInt(id));
    }

    @GetMapping(value = "/getCourseTeachers/{courseId}")
    public List<Teacher> getCourseTeachers(@PathVariable("courseId") String id) {
        return courseService.getCourseTeachers(Integer.parseInt(id));
    }

    @GetMapping(value = "/getCourseStudents/{courseId}")
    public List<Student> getCourseStudents(@PathVariable("courseId") String id) {
        return courseService.getCourseStudents(Integer.parseInt(id));
    }

    @PutMapping(value = "/addTeacher/{courseId}/{teacherId}")
    public ResponseEntity addTeacherToCourse(@PathVariable("courseId") String courseId,
                                             @PathVariable("teacherId") String teacherId) {
        try {
            courseService.addTeacherToCourse(Integer.parseInt(courseId), Integer.parseInt(teacherId));
            return ResponseEntity.ok()
                    .body("teacher added");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("error " + e.getMessage());
        }
    }

    @PutMapping(value = "/addUser/{courseId}/{userId}")
    public ResponseEntity addUserToCourse(@PathVariable("courseId") String courseId,
                                          @PathVariable("userId") String userId) {
        try {
            courseService.addUserToCourse(Integer.parseInt(courseId), Integer.parseInt(userId));
            return ResponseEntity.ok()
                    .body("user added.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("error " + e.getMessage());
        }
    }

    @GetMapping(value = "/studentsToAdd/{courseId}", produces = "application/json")
    public List<AcademicUser> getStudentsNotInCourse(@PathVariable("courseId") String courseId) {
        try {
            return courseService.getStudentsNotInCourse(Integer.parseInt(courseId));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/teachersToAdd/{courseId}", produces = "application/json")
    public List<Teacher> getTeachersNotInCourse(@PathVariable("courseId") String courseId) {
        try {
            return courseService.getTeachersNotInCourse(Integer.parseInt(courseId));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping(value = "/deleteUser/{courseId}/{userId}")
    public ResponseEntity deleteUser(@PathVariable("courseId") String courseId,
                                     @PathVariable("userId") String userId) {
        try {
            courseService.deleteUser(Integer.parseInt(courseId), Integer.parseInt(userId));
            return ResponseEntity.ok()
                    .body("successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("error " + e.getMessage());
        }
    }


    @DeleteMapping(value = "/delete/{courseId}")
    public ResponseEntity delete(@PathVariable("courseId") String courseId) {
        try {
            courseService.deleteCourse(Integer.parseInt(courseId));
            return ResponseEntity.ok()
                    .body("course deleted.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("error " + e.getMessage());
        }
    }

}
