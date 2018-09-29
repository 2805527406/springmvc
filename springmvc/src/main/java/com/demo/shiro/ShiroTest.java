package com.demo.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;

public class ShiroTest {
	private static final SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
	public static void addUser() {
		simpleAccountRealm.addAccount("zhangsan","123456","admin","user");
	}
	public static void main(String[] args) {
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(simpleAccountRealm);
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","123456");
		addUser();
		subject.login(token);
		subject.checkRoles("admin","");
		System.out.println("isAuthenticated:"+ subject.isAuthenticated());
		subject.logout();
		System.out.println("isAuthenticated:"+ subject.isAuthenticated());
	}
}
