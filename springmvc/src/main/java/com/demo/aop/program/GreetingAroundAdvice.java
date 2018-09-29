package com.demo.aop.program;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
/**
 * 编程式增强
 * 环绕增强
 * @author Administrator
 *
 */
public class GreetingAroundAdvice implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		System.out.println("环绕前");
		String name = arg0.getMethod().getName();
		if(name == "sayHello") {
			System.out.println("sayHello 搞特殊");
		}
		Object object = arg0.proceed();//目标方法
		System.out.println("环绕后");
		return object;
	}
	
	public static void main(String[] args) {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(new GreetingImpl());
		proxyFactory.addAdvice(new GreetingAroundAdvice());
		Greeting geeting = (Greeting) proxyFactory.getProxy();
		geeting.sayHello("sdfad");
		geeting.name("12321");
	}

}
