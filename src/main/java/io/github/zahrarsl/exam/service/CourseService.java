package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.CourseDao;
import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseDao courseDao;
    private AcademicUserService academicUserService;

    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Autowired
    public void setAcademicUserService(AcademicUserService academicUserService) {
        this.academicUserService = academicUserService;
    }

    public List<Course> getAll(){
        return courseDao.findAll();
    }

    public Course save(Course course){
        return courseDao.save(course);
    }

    public Course getCourse(int id){
        return courseDao.getById(id);
    }

    public List<AcademicUser> getCourseTeachers(int id){
        //todo handle error scenarios and throw exception
        return courseDao.getCourseTeachers(id);
    }

    public List<AcademicUser> getCourseStudents(int id){
        //todo
        return courseDao.getCourseStudents(id);
    }

    public Course addTeacherToCourse(int courseId, int teacherId) throws Exception{
        Course course = getCourse(courseId);
        AcademicUser user = academicUserService.getUser(teacherId);
        if (course.getTeachers().contains(user)){
            throw new Exception("duplicate user");
        }
        if (user.getRole().equals("TEACHER")) {
            course.getTeachers().add(user);
            return save(course);
        }
        else {
            throw new Exception("this user is not a teacher!");
        }
    }


    public Course addStudentToCourse(int courseId, int studentId) throws Exception{
        Course course = getCourse(courseId);
        AcademicUser user = academicUserService.getUser(studentId);
        if (course.getStudents().contains(user)){
            throw new Exception("duplicate user");
        }
        if (user.getRole().equals("STUDENT")) {
            course.getStudents().add(user);
            return save(course);
        }
        else {
            throw new Exception("this user is not a student!");
        }
    }

    public Course addUserToCourse(int courseId, int userId) throws Exception{
        AcademicUser user = academicUserService.getUser(userId);
        Course course = getCourse(courseId);
        if (user.getRole().equals("STUDENT")){
            return addStudentToCourse(courseId, userId);
        }else if (user.getRole().equals("TEACHER")){
            return addTeacherToCourse(courseId, userId);
        }
        else {
            throw new Exception("user is invalid to add to course");
        }
    }

    public List<AcademicUser> getTeachersNotInCourse(int courseId){
        List<AcademicUser> teachers = academicUserService.getTeachers();
        Course course = courseDao.getById(courseId);
        teachers.removeAll(course.getTeachers());
        return teachers;
    }

    public List<AcademicUser> getStudentsNotInCourse(int courseId){
        List<AcademicUser> students = academicUserService.getStudents();
        Course course = courseDao.getById(courseId);
        students.removeAll(course.getStudents());
        return students;
    }

    public void deleteUser(int courseId, int userId) throws Exception{
        Course course = getCourse(courseId);
        AcademicUser user = academicUserService.getUser(userId);
        boolean teacherRemove = course.getTeachers().remove(user);
        if (!teacherRemove) {
            course.getStudents().remove(user);
        }
        courseDao.save(course);
    }

    public void deleteCourse(int courseId) throws Exception{
        if (getCourse(courseId) != null) {
            courseDao.deleteById(courseId);
        } else {
            throw new Exception("course not found");
        }
    }
}
