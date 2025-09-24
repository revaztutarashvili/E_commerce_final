package com.nabiji.ecommerce.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * This advice will be executed around any method annotated with @LogExecutionTime.
     * @param joinPoint The execution point of the intercepted method.
     * @return The result of the original method's execution.
     * @throws Throwable if the original method throws an exception.
     */
    @Around("@annotation(com.nabiji.ecommerce.aspect.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Proceed with the original method execution
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        logger.info("'{}' executed in {} ms", joinPoint.getSignature().toShortString(), executionTime);

        return result;
    }
}