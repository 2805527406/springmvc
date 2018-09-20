package com.demo.quartz.QuartzAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.quartz.entity.JobInfo;
import com.demo.quartz.service.IQuartzJobMng;

@Controller
public class QuartzAction {
//	@Autowired
//	private IQuartzJobMng quarzJonMng;
//	
//	@RequestMapping(value="/quartz/getList")
//	public ModelAndView getList(@RequestParam(value="keyword",required=false) String keyword) {
//		ModelAndView mav = new ModelAndView();
//		Map<String, String> params = new HashMap<String,String>();
//		if(StringUtils.isNotBlank(keyword)){
//			params.put("keyword", keyword);
//		}
//		System.out.println("½øÈë");
//		List<JobInfo> list =  quarzJonMng.queryInfo(params);
//		mav.addObject("list", list);
//		mav.addObject("keyword",keyword);
//		mav.setViewName("hello");
//		return mav;
//	}
	
}
