package com.wenda.aspect;

import com.wenda.dateroute.DynamicDataSourceHolder;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 定义数据源的AOP切面，通过该Service的方法名判断是应该走读库还是写库
 *
 * @author evilhex.
 * @date 2018/7/26 下午6:32.
 */
@Aspect
@Component
public class DataSourceAspect {

    @Before("execution(* com.wenda.service.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        if (isSlave(methodName)) {
            DynamicDataSourceHolder.markSlave();
        } else {
            DynamicDataSourceHolder.markMaster();
        }
    }

    /**
     * 判断是否为读库
     *
     * @param methodName
     * @return
     */
    private Boolean isSlave(String methodName) {
        // 方法名以query、find、get开头的方法名走从库
        return StringUtils.startsWith(methodName, "query") || StringUtils.startsWith(methodName, "find") || StringUtils.startsWith(methodName, "get");
    }

}
