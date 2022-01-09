package edu.neu.customertracker.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

  @Pointcut("execution(* edu.neu.customertracker.controller.*.*(..))")
  private void forControllerPackage() {
  }

  @Pointcut("execution(* edu.neu.customertracker.service.*.*(..))")
  private void forServicePackage() {
  }

  @Pointcut("execution(* edu.neu.customertracker.dao.*.*(..))")
  private void forDaoPackage() {
  }

  @Pointcut("forControllerPackage() || forServicePackage()||forDaoPackage()")
  private void forAppFlow() {
  }

  @Before("forAppFlow()")
  public void before(JoinPoint joinPoint) {
    String method = joinPoint.getSignature().toShortString();
    System.out.println("======>> in @Before: calling method: " + method);

    Object[] args = joinPoint.getArgs();
    for (Object arg : args) {
      System.out.println("======>> argument: " + arg);
    }
  }

  @AfterReturning(
      pointcut = "forAppFlow()",
      returning = "result"
  )
  public void afterReturning(JoinPoint joinPoint, Object result) {
    String method = joinPoint.getSignature().toShortString();
    System.out.println("======>> in @AfterReturning: calling method: " + method);

    System.out.println("======>> result " + result);
  }
}
