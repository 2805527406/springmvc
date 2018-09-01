package com.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DemoInterceptor implements HandlerInterceptor{
	
	/**��
	 * �÷������������������֮��Ҳ������DispatcherServlet ��Ⱦ�˶�Ӧ����ͼ֮��ִ��
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("after Completion");
	}
	
	/**
	 * postHandle ����������˼������ڵ�ǰ������д���֮��
	 * Ҳ����Controller ��������֮��ִ�У�����������DispatcherServlet 
	 * ������ͼ������Ⱦ֮ǰ�����ã��������ǿ�������������ж�Controller ����֮���ModelAndView ������в���
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("post Handle");
	}
	
	/**
	 * �÷�������������֮ǰ���е���
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle");
		return true;
	}
	
	
}
