package io.github.zahrarsl.exam.aspect;

import io.github.zahrarsl.exam.model.entity.AcademicUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@Aspect
@Component
public class AcademicUserLoggingAspect {

    private static final Logger logger = LogManager.getLogger(AcademicUser.class);

    @AfterReturning("academicUserSave()")
    public void saveAspect(JoinPoint joinPoint){
        AcademicUser user = (AcademicUser)joinPoint.getArgs()[0];
        logger.info("user with email = " + user.getEmail() + " saved or updated.");
    }

    @After("academicUserDelete()")
    public void deleteAspect(JoinPoint joinPoint){
        int id = (Integer)joinPoint.getArgs()[0];
        logger.info("user with id = " + id + " deleted.");
    }

    @After("verifyUser()")
    public void verifyAspect(JoinPoint joinPoint){
        int id = (Integer)joinPoint.getArgs()[0];
        logger.info("user with id = " + id + " verified by admin.");
    }

    @Pointcut("execution(* io.github.zahrarsl.exam.service.AcademicUserService.save(..))")
    public void academicUserSave(){}

    @Pointcut("execution(* io.github.zahrarsl.exam.model.dao.AcademicUserDao.delete*(..))")
    public void academicUserDelete(){}

    @Pointcut("execution(* io.github.zahrarsl.exam.model.dao.AcademicUserDao.setAdminVerification*())")
    public void verifyUser(){}
}
