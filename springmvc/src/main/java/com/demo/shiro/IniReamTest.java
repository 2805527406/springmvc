package com.demo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class IniReamTest {
	private static IniRealm iniRealm = new IniRealm("classpath:user.ini");
	public static void main(String[] args) {
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(iniRealm);
		
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","123456");
		subject.login(token);
		System.out.println("isAuthenticated:"+subject.isAuthenticated());//
		subject.checkRoles("admin");
		subject.checkPermission("haha");
		subject.checkPermissions("user:delete","user:update","haha");
	}
}
