package com.demo.aop.introduction;

public class LoveImpl implements Love{

	@Override
	public void display(String name) {
		System.out.println("display:"+name);
	}
	
}
