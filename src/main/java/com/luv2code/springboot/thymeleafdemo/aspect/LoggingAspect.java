package com.luv2code.springboot.thymeleafdemo.aspect;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.controller.EmployeeController.*(..))")
    private void controllerPointcut() {}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.*(..))")
    private void servicePointcut() {}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.dao.*.*(..))")
    private void daoPointcut() {}

    @Pointcut("controllerPointcut() || servicePointcut() || daoPointcut()")
    private void appFlowPointcut() {}

    @Before("appFlowPointcut()")
    public void beforeAdvice(JoinPoint joinPoint) {

        String methodSignature = joinPoint.getSignature().toShortString();
        LOG.info("=====> method " + methodSignature + " called");

        Object[] args = joinPoint.getArgs();
        for (final var arg: args) {
            LOG.info("=====> arg: " + arg);
        }

    }

    @AfterReturning(pointcut = "appFlowPointcut()",
        returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        String methodSignature = joinPoint.getSignature().toShortString();
        LOG.info("=====> method " + methodSignature + " finished execution");

        LOG.info("=====> returned " + result);
    }
}
