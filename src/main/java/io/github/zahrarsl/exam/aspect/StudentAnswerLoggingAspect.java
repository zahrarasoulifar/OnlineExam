package io.github.zahrarsl.exam.aspect;

import io.github.zahrarsl.exam.model.entity.StudentAnswer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StudentAnswerLoggingAspect {
    private static final Logger logger = LogManager.getLogger(StudentAnswer.class);

    @AfterReturning("answerSave()")
    public void saveAspect(JoinPoint joinPoint){
        StudentAnswer answer = (StudentAnswer) joinPoint.getArgs()[0];
        logger.info("student answer with content = " + answer.getContent() + " saved or updated. student id: " +
                answer.getStudent().getId());
    }

    @AfterThrowing("answerSave()")
    public void failingSaveAspect(JoinPoint joinPoint){
        StudentAnswer answer = (StudentAnswer) joinPoint.getArgs()[0];
        logger.info("student answer with content = " + answer.getContent() + " failed to be saved or updated. student id: " +
                answer.getStudent().getId());
    }

    @Pointcut("execution(* io.github.zahrarsl.exam.model.dao.StudentAnswerDao.save(..))")
    public void answerSave(){}
}
