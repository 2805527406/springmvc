package com.demo.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.demo.entity.People;
import com.demo.quartz.entity.JobInfo;
import com.demo.quartz.service.IQuartzJobMng;
import com.demo.redis.RedisUtil;
import com.demo.service.base.impl.PeopleService;
import com.demo.util.MD5Util;

@Controller
public class mvcController {
	private final static String TOKEN="ggl";
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
	  
	  
	  @Autowired
		private IQuartzJobMng quarzJonMng;
		
		@RequestMapping(value="/quartz/getList")
		public ModelAndView getList(@RequestParam(value="keyword",required=false) String keyword) {
			ModelAndView mav = new ModelAndView();
			Map<String, String> params = new HashMap<String,String>();
			if(StringUtils.isNotBlank(keyword)){
				params.put("keyword", keyword);
			}
			System.out.println("进入");
			List<JobInfo> list =  quarzJonMng.queryInfo(params);
			mav.addObject("list", list);
			mav.addObject("keyword",keyword);
			mav.setViewName("hello");
			return mav;
		}
	  
}
