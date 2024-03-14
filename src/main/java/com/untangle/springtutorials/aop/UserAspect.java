package com.untangle.springtutorials.aop;

import java.util.Date;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.untangle.springtutorials.dto.User;

@Aspect
@Component
public class UserAspect {

    // @Before(value = "execution(* com.untangle.springtutorials.controller.UserController.*(..))")
    // public void beforeAdvice(JoinPoint joinPoint){
    //     String className = joinPoint.getTarget().getClass().getName();
    //     String methodName = joinPoint.getSignature().getName();
    //     System.out.println("##Method Invoked "+className+" :: "+methodName+"() at "+new Date());
    // }

    // @After(value = "execution(* com.untangle.springtutorials.controller.UserController.*(..))")
    // public void afterAdvice(JoinPoint joinPoint){
    //     String className = joinPoint.getTarget().getClass().getName();
    //     String methodName = joinPoint.getSignature().getName();
    //     System.out.println("##Method Executed "+className+" :: "+methodName+"() at "+new Date());
    // }

    // @AfterReturning(value = "execution(* com.untangle.springtutorials.controller.UserController.getUsers(..))", returning = "users")
    // public void afterReturningAdvice(JoinPoint joinPoint,List<User> users){
    //     String className = joinPoint.getTarget().getClass().getName();
    //     String methodName = joinPoint.getSignature().getName();
    //     System.out.println("##Business logic for getting users"+className+" "+methodName+"() executed successfully with user count :: "+ users.size());
    // }

    // @AfterThrowing(value = "execution(* com.untangle.springtutorials.controller.UserController.getUsers(..))", throwing = "exception")
    // public void afterThrowingAdvice(JoinPoint joinPoint,Exception exception){
    //     String className = joinPoint.getTarget().getClass().getName();
    //     String methodName = joinPoint.getSignature().getName();
    //     System.out.println("##Business logic for getting users"+className+" "+methodName+"() execution failed with exception:: "+ exception.getMessage());
    // }

    @Around(value="execution(* com.untangle.springtutorials.controller.UserController.getUsers(..))")
    public List<User> aroundAdvice(ProceedingJoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println("##Business logic for getting users"+className+" "+methodName+"() execution started from Around Advice:: "+ new Date());
        try {
            List<User> users = (List<User>)joinPoint.proceed();
            return users;
        } catch (Throwable e) {
            System.out.println("##getUsers executed failed, from Around Advice "+e.getMessage());
        }
        System.out.println("##Business logic for getting users"+className+" "+methodName+"() execution finished from Around Advice:: "+ new Date());
        return null;
    }



}
