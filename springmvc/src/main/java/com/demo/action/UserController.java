package com.demo.action;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	/**
	 * 猜想：测试过滤器不成功
	 * 1.没有经过login方法进行账号验证 所以一直导致角色获取不到
	 * @return
	 */
	@RequestMapping(value="/shiro/toLogin")
	public String index() {
		return "login";
	}
	
	@RequestMapping(value="/shiro/login",method=RequestMethod.POST,produces="application/json; charset=utf-8")
	@ResponseBody
	public String login(@RequestParam String username,@RequestParam String password) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		try {
			token.setRememberMe(true);//是否记住密码
			subject.login(token);
			if(subject.hasRole("admin")) {
				return "有admin权限";
			}else {
				return "没有admin权限";
			}
		} catch (Exception e) {
			return "账号或密码错误";
		}
	}
	
	@RequestMapping(value="/shiro/testRole3",method = RequestMethod.GET)
	@ResponseBody
	public String testRole3() {
		return "testRole success3";
	}
	@RequestMapping(value="/shiro/testRole4",method = RequestMethod.GET)
	@ResponseBody
	public String testRole4() {
		return "testRole success4";
	}
	@RequiresRoles("admin")
	@RequestMapping(value="/shiro/testRole2",method = RequestMethod.GET)
	@ResponseBody
	public String testRole() {
		return "testRole success";
	}
	
	//注解方式
	//具有相应角色和权限的才能进入该方法
	@RequiresRoles("admin1")
	@RequiresPermissions({"",""})
	@RequestMapping(value="/shiro/testRole1",method = RequestMethod.GET)
	@ResponseBody
	public String testRole1() {
		return "testRole success1";
	}
	
}
