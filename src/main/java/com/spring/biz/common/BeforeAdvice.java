package com.spring.biz.common;

import org.aspectj.lang.JoinPoint;

public class BeforeAdvice {
	public void beforeLog(JoinPoint jp) {
		String method = jp.getSignature().getName();
		Object[] args = jp.getArgs();
		
		System.out.println("[사전 처리]" + method + "() 메소드 ARGS 정보 :" + args[0].toString());
		
//		System.out.println("[사전 처리] 비지니스 로직 수행 전 동작");
	}
}
