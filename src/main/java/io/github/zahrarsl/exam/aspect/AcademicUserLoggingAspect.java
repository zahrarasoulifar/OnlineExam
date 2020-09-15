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

    @AfterReturning("saveAcademicUser()")
    public void saveAspect(JoinPoint joinPoint){
        AcademicUser user = (AcademicUser)joinPoint.getArgs()[0];
        logger.info("user with email = " + user.getEmail() + " saved or updated.");
    }

    @AfterThrowing("saveAcademicUser()")
    public void failingSaveAspect(JoinPoint joinPoint){
        AcademicUser user = (AcademicUser)joinPoint.getArgs()[0];
        logger.error("academic user with email = " + user.getEmail() + " failed to save or update.");
    }

    @AfterReturning("deleteAcademicUser()")
    public void deleteAspect(JoinPoint joinPoint){
        int id = (Integer)joinPoint.getArgs()[0];
        logger.info("user with id = " + id + " deleted.");
    }

    @AfterThrowing("deleteAcademicUser()")
    public void failingDeleteAspect(JoinPoint joinPoint){
        int id = (Integer)joinPoint.getArgs()[0];
        logger.error("user with id = " + id + " failed to be deleted.");
    }

    @AfterReturning("verifyUser()")
    public void verifyAspect(JoinPoint joinPoint){
        int id = (Integer)joinPoint.getArgs()[0];
        logger.info("user with id = " + id + " is verified by admin.");
    }

    @AfterThrowing("verifyUser()")
    public void failingVerifyAspect(JoinPoint joinPoint){
        int id = (Integer)joinPoint.getArgs()[0];
        logger.info("user with id = " + id + " failed to be verified by admin.");
    }

    @Pointcut("execution(* io.github.zahrarsl.exam.service.AcademicUserService.save(..))")
    public void saveAcademicUser(){}

    @Pointcut("execution(* io.github.zahrarsl.exam.model.dao.AcademicUserDao.delete*(..))")
    public void deleteAcademicUser(){}

    @Pointcut("execution(* io.github.zahrarsl.exam.model.dao.AcademicUserDao.setAdminVerification*())")
    public void verifyUser(){}
}
