package com.demo.aop.program;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
/**
 * ���ʽ��ǿ
 * ������ǿ
 * @author Administrator
 *
 */
public class GreetingAroundAdvice implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		System.out.println("����ǰ");
		String name = arg0.getMethod().getName();
		if(name == "sayHello") {
			System.out.println("sayHello ������");
		}
		Object object = arg0.proceed();//Ŀ�귽��
		System.out.println("���ƺ�");
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
