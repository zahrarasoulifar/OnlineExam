package io.github.zahrarsl.exam.aspect;

import io.github.zahrarsl.exam.model.entity.Question;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class QuestionLoggingAspect {
    private static final Logger logger = LogManager.getLogger(Question.class);

    @AfterReturning("questionSave()")
    public void saveAspect(JoinPoint joinPoint){
        Question question = (Question) joinPoint.getArgs()[0];
        logger.info("question with title = " + question.getTitle() + " saved or updated. teacher id: "
                + question.getTeacher().getId() + ". type: " + question.getClass());
    }

    @AfterThrowing("questionSave()")
    public void failingSaveAspect(JoinPoint joinPoint){
        Question question = (Question) joinPoint.getArgs()[0];
        logger.error("question with title = " + question.getTitle() + " failed to be saved or updated. teacher id: "
                + question.getTeacher().getId());
    }

    @Pointcut("execution(* io.github.zahrarsl.exam.model.dao.QuestionDao.save(..))")
    public void questionSave(){}
}
