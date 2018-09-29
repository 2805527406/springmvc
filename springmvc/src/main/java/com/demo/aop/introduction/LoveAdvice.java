package com.demo.aop.introduction;

import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class LoveAdvice extends DelegatingIntroductionInterceptor implements Love{

	@Override
	public void display(String name) {
		
	}

}
