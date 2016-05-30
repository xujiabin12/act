package com.act.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import com.act.util.IdBuilder;



@Aspect
@Service
public class MyControllerAspect {
	
	@Pointcut("execution(public * com.act.controller..*.*(..))")
    private void controller(){
	}
	
	

    @Around("controller()")
    public Object setViewMDC(ProceedingJoinPoint pjp) throws Throwable{
        try {
        	MDC.put("requestId", IdBuilder.getID());
        	return pjp.proceed();
        }finally{
        	MDC.remove("requestId");
        }
    }

}
