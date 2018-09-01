package com.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DemoInterceptor implements HandlerInterceptor{
	
	/**、
	 * 该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("after Completion");
	}
	
	/**
	 * postHandle 方法，顾名思义就是在当前请求进行处理之后，
	 * 也就是Controller 方法调用之后执行，但是它会在DispatcherServlet 
	 * 进行视图返回渲染之前被调用，所以我们可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("post Handle");
	}
	
	/**
	 * 该方法将在请求处理之前进行调用
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle");
		return true;
	}
	
	
}
