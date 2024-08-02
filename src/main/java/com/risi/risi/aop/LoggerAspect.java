package com.risi.risi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component    // SpringBoot에서는 @EnableAspectJAutoProxy 애노테이션을 추가하지 않아도 자동으로 AOP 설정을 활성화
@Slf4j
@Aspect
public class LoggerAspect {

    @Pointcut("execution(* board..controller.*Controller.*(..)) || execution(* board..service.*Impl.*(..)) || execution(* board..mapper.*Mapper.*(..))")
    private void loggerTarget() {

    }

    @Around("loggerTarget()")
    public Object logPrinter(ProceedingJoinPoint joinPoint) throws Throwable {
        String type = "";
        String className = joinPoint.getSignature().getDeclaringTypeName();
        if (className.contains("Controller")) {
            type = "[Controller]";
        } else if (className.contains("Service")) {
            type = "[Service]";
        } else if (className.contains("Mapper")) {
            type = "[Mapper]";
        }
        String methodName = joinPoint.getSignature().getName();

        log.debug(type + " " + className + "." + methodName);
        return joinPoint.proceed();
    }
}
