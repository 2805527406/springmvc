package com.demo.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.demo.entity.People;
import com.demo.redis.RedisUtil;
import com.demo.service.base.impl.PeopleService;

@Controller
public class mvcController {
	@Autowired
	private PeopleService service;
	@Autowired
	private RedisUtil redisUtil;
	private  static Logger logger = LoggerFactory.getLogger(mvcController.class);
	@RequestMapping(value="/hello")
	public String hello(Model model) {
		System.out.println("进入hello方法");
		try {
//			List<People> list = service.getAllByHQL("from People");
			List<People> list = new ArrayList<>();
			People people = service.load(1);
			list.add(people);
			model.addAttribute("list",list);
			redisUtil.set("people",JSONObject.toJSONString(people));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		logger.info("方法进入");
		return "hello";
	}
	  @RequestMapping("/error")
	    public String error(){
	        @SuppressWarnings("unused")
			int i = 5/0;
	        return ""
	        		+ "hello";
	    }
}
