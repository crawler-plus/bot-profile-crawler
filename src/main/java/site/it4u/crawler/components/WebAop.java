package site.it4u.crawler.components;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class WebAop {

    @Pointcut("execution(* site.it4u.crawler.controller.*.*(..))")
    private void webLog() {
    }

    @Around("webLog()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        long timeStart = System.currentTimeMillis();
        Object obj = proceedingJoinPoint.proceed();
        log.info("execute method: {}", methodName);
        log.info("execute time: {}{}", (System.currentTimeMillis() - timeStart), "ms");
        return obj;
    }
}