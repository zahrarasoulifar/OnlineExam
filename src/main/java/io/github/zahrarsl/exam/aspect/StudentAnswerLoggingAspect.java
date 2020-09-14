package io.github.zahrarsl.exam.aspect;

import io.github.zahrarsl.exam.model.entity.AcademicUser;
import io.github.zahrarsl.exam.model.entity.Exam;
import io.github.zahrarsl.exam.model.entity.StudentAnswer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StudentAnswerLoggingAspect {
    private static final Logger logger = LogManager.getLogger(StudentAnswer.class);

    @After("answerSave()")
    public void saveAspect(JoinPoint joinPoint){
        StudentAnswer answer = (StudentAnswer) joinPoint.getArgs()[0];
        logger.info("answer with content = " + answer.getContent() + " saved or updated. user id: " + answer.getStudent().getId());
    }

    @Pointcut("execution(* io.github.zahrarsl.exam.model.dao.StudentAnswerDao.save(..))")
    public void answerSave(){}
}
