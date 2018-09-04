package com.demo.action;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.demo.util.MD5Util;
import com.demo.util.WeixinUtil;
import com.demo.weixin.button.InitMenu;

@Controller
@RequestMapping(value="/Wei")
public class AalidateController {
	private final static String TOKEN="ggl";
	@RequestMapping("/weixin")
	@ResponseBody
	public String tokenCheck(HttpServletRequest request){
		System.out.println("进入");
		//tonken校验
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		System.out.println("signature:" + signature+"，timestamp:" + timestamp+"，nonce:" + nonce+"，echostr:" + echostr);
		String[] str = {TOKEN,timestamp,nonce};
		Arrays.sort(str);
		String bigStr = str[0]+str[1]+str[2];
		String digest =DigestUtils.sha1Hex(bigStr);
		if(signature.equals(digest)) {
			return echostr;
		}else {
			System.out.println("token验证异常");
			return null;
		}
		
	}
	@RequestMapping("/createMenu")
	@ResponseBody
	public int createWxMenu() {
		try {
			int result = WeixinUtil.createMenu(JSONArray.toJSONString(InitMenu.initMenu()));
			if(result == 0) {
				System.out.println("创建成功");
			}else {
				System.out.println("创建失败");
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
}
