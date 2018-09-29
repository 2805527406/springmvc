package com.demo.aop.program;

public class GreetingImpl implements Greeting{

	@Override
	public void sayHello(String name) {
		System.out.println("Hello "+name);
	}

	@Override
	public void name(String sss) {
		System.out.println("วรฃบ"+sss);
	}

}
