package com.demo.aop.program;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 
 * @author Administrator
 * 编程式增强
 *前置增强和后置增强（这里我将两个增强合并，即实现两个接口）
 */
public class GreetingBeforAndAfterAdvice implements MethodBeforeAdvice,AfterReturningAdvice{
/**
 * 前
 */
	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		System.out.println("Before");
	}
/**
 * 后
 */
	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
		System.out.println("After");
	}
	public static void main(String[] args) {
//		ProxyFactory proxyFactory = new ProxyFactory();//创建代理工厂
//		proxyFactory.setTarget(new GreetingImpl());//射入目标类对象
//		proxyFactory.addAdvice(new GreetingBeforAndAfterAdvice());//添加前置增强和后置增强
//		Greeting greeting = (Greeting) proxyFactory.getProxy();
//		greeting.sayHello("jack");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:aop.xml");
		Greeting greeting = (Greeting) context.getBean("greetingProxy");
		greeting.sayHello("sdf");
	}

}
