package com.wenda.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author evilhex
 * Created by evilhex on 2017/9/3.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * controller处理之前
     *
     * @param joinPoint
     */
    @Before("execution(* com.wenda.controller.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            if (arg != null) {
                sb.append("arg: " + arg.toString() + "|");
            }
        }
        logger.info("before method:" + sb.toString());

    }

    /**
     * controller处理之后
     */
    @After("execution(* com.wenda.controller.*.*(..))")
    public void afterMethod() {
        logger.info("after method");
    }
}
