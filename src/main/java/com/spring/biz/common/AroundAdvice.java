package com.spring.biz.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class AroundAdvice {
	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
		String method = pjp.getSignature().getName();
		
//		System.out.println("[BEFORE] 비지니스 메소드 수행 전에 처리할 내용...");
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		
		Object returnObj = pjp.proceed();
		
		stopwatch.stop();
		System.out.println(method + "() 메소드 수행에 걸린 시간 : "
				+ stopwatch.getTotalTimeMillis() + "(ms)초");
//		System.out.println("[AFTER] 비지니스 메소드 수행 후에 처리할 내용...");
		
		return returnObj;
	}
}
