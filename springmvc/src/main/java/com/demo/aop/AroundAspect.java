package com.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.demo.aop.introduction.Love;
import com.demo.aop.introduction.LoveImpl;
import com.demo.aop.program.Greeting;
import com.demo.aop.program.GreetingImpl;

/**
 * . AspectJ execution 表达式拦截
 * @author Administrator
 *
 */
@Aspect
@Component
public class AroundAspect {
	// AspectJ @DeclareParents 注解（引入增强）
	@DeclareParents(value="com.demo.aop.program.GreetingImpl",defaultImpl=LoveImpl.class)
	private Love love;
/**
 * https://www.cnblogs.com/haore147/p/7212504.html
 * 
 */
	@Around("execution(* com.demo.aop.AroundAspect.*.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		before();
		Object object = pjp.proceed();
		after();
		return object;
	}
	
	private void before() {
		System.out.println("再环绕Before");
	}
	
	private void after() {
		System.out.println("再环绕After");
	}
	public static void main(String[] args) {
		ApplicationContext context =new ClassPathXmlApplicationContext("classpath:/aop7.xml");
		Greeting greeting  = (Greeting) context.getBean("greetingImpl");
		GreetingImpl greetingImpl = (GreetingImpl) greeting;
		greetingImpl.sayHello("dsfasd");
		greetingImpl.goodMorning("张三");
		Love love = (Love) greeting;
		love.display("234ed");
	}
}
