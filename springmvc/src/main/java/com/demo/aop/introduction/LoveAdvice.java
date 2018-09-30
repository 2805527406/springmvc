package com.demo.aop.introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.aop.program.GreetingImpl;

public class LoveAdvice extends DelegatingIntroductionInterceptor implements Love{
	
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		return super.invoke(mi);
	}
	@Override
	public void display(String name) {
		System.out.println("引入增强："+name);
	}
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/aop4.xml");
		//自动代理
		GreetingImpl greetingImpl = (GreetingImpl) context.getBean("greetingImpl");
		greetingImpl.sayHello("eerr");
		greetingImpl.goodMorning("sdfsdf");
//		Love love = (Love) greetingImpl;
//		love.display("sdfsd");
		
		
		
	}
}
