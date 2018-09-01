package com.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MatTypeCacheController{
	@Autowired
	private RedisUtil redisUtil;
	
}

