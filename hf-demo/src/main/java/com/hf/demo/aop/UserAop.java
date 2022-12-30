package com.hf.demo.aop;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author xiehongfei
 * @description
 * @date 2022/12/30 20:33
 */
@Component
@Slf4j(topic = "UserAop")
@Aspect
public class UserAop {


    @Pointcut("execution(* com.hf.demo.controller..*(..))")
    private void point() {
    }

    @Before("point()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("Aop do before-{}, args:{}", joinPoint.getSignature().getDeclaringTypeName(), Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning("point()")
    public void doAfterReturning1(JoinPoint joinPoint) {
        log.info("Aop do doAfterReturning1-{}, args:{}", joinPoint.getSignature().getDeclaringTypeName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "point()", returning = "user")
    public void doAfterReturning2(JoinPoint joinPoint, Object user) {
        log.info("Aop do doAfterReturning2-{}, args:{}",
                joinPoint.getSignature().getDeclaringTypeName(), Arrays.toString(joinPoint.getArgs()));
        log.info("doAfterReturning2-{}", user);
    }

    @AfterThrowing(pointcut = "point()", throwing = "throwable")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        log.info("Aop do AfterThrowing-{}, args:{}",
                joinPoint.getSignature().getDeclaringTypeName(), Arrays.toString(joinPoint.getArgs()));
        log.info("AfterThrowing", throwable);
    }

    @After("point()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("Aop do AfterThrowing-{}, args:{}",
                joinPoint.getSignature().getDeclaringTypeName(), Arrays.toString(joinPoint.getArgs()));
    }

    @SneakyThrows
    @Around("point()")
    public void doAround(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("Aop do around-start");
        proceedingJoinPoint.proceed();
        log.info("Aop do around-end");
    }
}
