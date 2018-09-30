package com.demo.aop.program;

import org.springframework.stereotype.Component;

@Component
public class GreetingImpl implements Greeting{

	@Override
	public void sayHello(String name) {
		System.out.println("Hello "+name);
	}

	@Override
	public void name(String sss) {
		System.out.println("敲："+sss);
	}
	
	public void goodMorning(String name) {
		System.out.println(name+"，早上好");
	}
	
	public void goodNight(String name) {
		System.out.println(name+"，晚上好");
	}

}
