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
 * ���ʽ��ǿ
 *ǰ����ǿ�ͺ�����ǿ�������ҽ�������ǿ�ϲ�����ʵ�������ӿڣ�
 */
public class GreetingBeforAndAfterAdvice implements MethodBeforeAdvice,AfterReturningAdvice{
/**
 * ǰ
 */
	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		System.out.println("Before");
	}
/**
 * ��
 */
	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
		System.out.println("After");
	}
	public static void main(String[] args) {
//		ProxyFactory proxyFactory = new ProxyFactory();//����������
//		proxyFactory.setTarget(new GreetingImpl());//����Ŀ�������
//		proxyFactory.addAdvice(new GreetingBeforAndAfterAdvice());//���ǰ����ǿ�ͺ�����ǿ
//		Greeting greeting = (Greeting) proxyFactory.getProxy();
//		greeting.sayHello("jack");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:aop.xml");
		Greeting greeting = (Greeting) context.getBean("greetingProxy");
		greeting.sayHello("sdf");
	}

}
