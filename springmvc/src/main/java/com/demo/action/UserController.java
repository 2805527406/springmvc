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
	 * ���룺���Թ��������ɹ�
	 * 1.û�о���login���������˺���֤ ����һֱ���½�ɫ��ȡ����
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
			token.setRememberMe(true);//�Ƿ��ס����
			subject.login(token);
			if(subject.hasRole("admin")) {
				return "��adminȨ��";
			}else {
				return "û��adminȨ��";
			}
		} catch (Exception e) {
			return "�˺Ż��������";
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
	
	//ע�ⷽʽ
	//������Ӧ��ɫ��Ȩ�޵Ĳ��ܽ���÷���
	@RequiresRoles("admin1")
	@RequiresPermissions({"",""})
	@RequestMapping(value="/shiro/testRole1",method = RequestMethod.GET)
	@ResponseBody
	public String testRole1() {
		return "testRole success1";
	}
	
}
