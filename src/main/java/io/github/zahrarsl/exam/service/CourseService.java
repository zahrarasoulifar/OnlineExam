package io.github.zahrarsl.exam.service;

import io.github.zahrarsl.exam.model.dao.CourseDao;
import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.Course;
import io.github.zahrarsl.exam.model.entity.Student;
import io.github.zahrarsl.exam.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private CourseDao courseDao;
    private AcademicUserService academicUserService;
    private TeacherService teacherService;
    private StudentService studentService;

    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Autowired
    public void setAcademicUserService(AcademicUserService academicUserService) {
        this.academicUserService = academicUserService;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
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

    public List<Teacher> getCourseTeachers(int id){
        return courseDao.getCourseTeachers(id);
    }

    public List<Student> getCourseStudents(int id){
        return courseDao.getCourseStudents(id);
    }

    public Course addTeacherToCourse(int courseId, int teacherId) throws Exception{
        Course course = getCourse(courseId);
        Teacher teacher = teacherService.getUser(teacherId);
        if (course.getTeachers().contains(teacher)){
            throw new Exception("duplicate user");
        }
        if (teacher.getRole().equals("TEACHER")) {
            course.getTeachers().add(teacher);
            return save(course);
        }
        else {
            throw new Exception("this user is not a teacher!");
        }
    }


    public Course addStudentToCourse(int courseId, int studentId) throws Exception{
        Course course = getCourse(courseId);
        Student student = studentService.getUser(studentId);
        if (course.getStudents().contains(student)){
            throw new Exception("duplicate user");
        }
        if (student.getRole().equals("STUDENT")) {
            course.getStudents().add(student);
            return save(course);
        }
        else {
            throw new Exception("this user is not a student!");
        }
    }

    public Course addUserToCourse(int courseId, int userId) throws Exception{
        AcademicUser user = academicUserService.getUser(userId);
        switch (user.getRole()) {
            case "STUDENT":
                return addStudentToCourse(courseId, userId);
            case "TEACHER":
                return addTeacherToCourse(courseId, userId);
            default:
                throw new Exception("user is invalid to add to course");
        }
    }

    public List<Teacher> getTeachersNotInCourse(int courseId){
        List<Teacher> teachers = academicUserService.getTeachers();
        Course course = courseDao.getById(courseId);
        teachers.removeAll(course.getTeachers());
        return teachers;
    }

    public List<AcademicUser> getStudentsNotInCourse(int courseId){
        List<AcademicUser> students = academicUserService.getVerifiedStudents();
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
