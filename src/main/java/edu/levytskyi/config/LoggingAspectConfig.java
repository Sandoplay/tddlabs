package edu.levytskyi.config;

/*
 @author Sandoplay
 @project lab1
 @class LoggingAspectConfig
 @version 1.0.0
 @since 15.04.2026 - 21.41
*/

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspectConfig {

  // Pointcut для всіх методів у CompanyServiceImpl
  @Pointcut("execution(* edu.levytskyi.service.CompanyServiceImpl.*(..))")
  public void companyServiceMethods() {}

  // Логування входу в метод
  @Before("companyServiceMethods()")
  public void logBefore(JoinPoint joinPoint) {
    log.info("Entering method: {} with args: {}",
        joinPoint.getSignature().getName(),
        Arrays.toString(joinPoint.getArgs()));
  }

  // Логування успішного завершення та результату
  @AfterReturning(pointcut = "companyServiceMethods()", returning = "result")
  public void logAfter(JoinPoint joinPoint, Object result) {
    log.info("Method {} executed successfully. Result: {}",
        joinPoint.getSignature().getName(), result);
  }
}