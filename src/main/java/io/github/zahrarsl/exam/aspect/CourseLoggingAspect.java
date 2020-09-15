package io.github.zahrarsl.exam.aspect;

import io.github.zahrarsl.exam.model.entity.Course;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CourseLoggingAspect {
    private static final Logger logger = LogManager.getLogger(Course.class);

    @AfterReturning("courseSave()")
    public void saveAspect(JoinPoint joinPoint){
        Course course = (Course)joinPoint.getArgs()[0];
        logger.info("course with title = " + course.getTitle() + " saved or updated.");
    }

    @AfterThrowing("courseSave()")
    public void failingSaveAspect(JoinPoint joinPoint){
        Course course = (Course)joinPoint.getArgs()[0];
        logger.error("course with title = " + course.getTitle() + " failed to be saved or updated.");
    }

    @AfterReturning("courseDelete()")
    public void deleteAspect(JoinPoint joinPoint){
        int id = (Integer)joinPoint.getArgs()[0];
        logger.info("course with id = " + id + " deleted.");
    }

    @AfterThrowing("courseDelete()")
    public void failingDeleteAspect(JoinPoint joinPoint){
        int id = (Integer)joinPoint.getArgs()[0];
        logger.error("course with id = " + id + " failed to be deleted.");
    }


    @Pointcut("execution(* io.github.zahrarsl.exam.model.dao.CourseDao.save(..))")
    public void courseSave(){}

    @Pointcut("execution(* io.github.zahrarsl.exam.model.dao.CourseDao.delete*(..))")
    public void courseDelete(){}

}
