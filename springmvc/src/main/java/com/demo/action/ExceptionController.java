package com.demo.action;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice//����ȫ���쳣
public class ExceptionController {
	@ExceptionHandler
	public ModelAndView exceptionHandler(Exception e) {
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("exception",e);
		e.printStackTrace();
		System.out.println("�쳣����");
		return mav;
	}
	
}
