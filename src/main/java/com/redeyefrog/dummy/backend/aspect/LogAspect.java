package com.redeyefrog.dummy.backend.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(public * com.redeyefrog.dummy.backend.controller.*.*(..))")
    public void controllerLog() {
    }

    @Around(value = "controllerLog()")
    public Object aroundController(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();

        Object obj = null;

        try {
            log.debug("around before [ controller: {}, method: {}, args: {} ]", signature.getDeclaringTypeName(), signature.getName(), point.getArgs());

            obj = point.proceed();

            log.debug("around after [ controller: {}, method: {}, obj: {} ]", signature.getDeclaringTypeName(), signature.getName(), obj);
        } catch (Throwable t) {
            log.error("after throw [ controller: {}, method: {}, throw: {} ]", signature.getDeclaringTypeName(), signature.getName(), t.getMessage(), t);

        } finally {
            log.debug("around after return [ controller: {}, method: {}, arg: {} ]", signature.getDeclaringTypeName(), signature.getName(), obj);

        }

        return obj;
    }

}
