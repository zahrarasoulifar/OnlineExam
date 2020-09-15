package io.github.zahrarsl.exam.aspect;

import io.github.zahrarsl.exam.model.entity.Exam;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExamLoggingAspect {
    private static final Logger logger = LogManager.getLogger(Exam.class);

    @AfterReturning("examSave()")
    public void saveAspect(JoinPoint joinPoint){
        Exam exam = (Exam) joinPoint.getArgs()[0];
        logger.info("exam with title = " + exam.getTitle() + " saved or updated. teacher id: " + exam.getTeacher().getId());
    }

    @AfterThrowing("examSave()")
    public void failingSaveAspect(JoinPoint joinPoint){
        Exam exam = (Exam) joinPoint.getArgs()[0];
        logger.info("exam with title = " + exam.getTitle() + " failed to be saved or updated. teacher id: "
                + exam.getTeacher().getId());
    }

    @Pointcut("execution(* io.github.zahrarsl.exam.model.dao.ExamDao.save(..))")
    public void examSave(){}

}
