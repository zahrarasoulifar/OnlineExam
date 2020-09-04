package io.github.zahrarsl.exam.aspect;

import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.Exam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExamLoggingAspect {
    private static final Logger logger = LogManager.getLogger(AcademicUser.class);

    @After("examSave()")
    public void saveAspect(JoinPoint joinPoint){
        Exam exam = (Exam) joinPoint.getArgs()[0];
        logger.info("exam with title = " + exam.getTitle() + " saved or updated. teacher id: " + exam.getTeacher().getId());
    }

    @Pointcut("execution(* io.github.zahrarsl.exam.model.dao.CourseDao.save(..))")
    public void examSave(){}

}
