package com.demo.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * 自定义授权验证方法
 * @author Administrator
 *
 */
public class CustomRealmTest {
	public static void main(String[] args) {
		CustomRealm customRealm = new CustomRealm();
		//1.构建SecurityMananger 环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(customRealm);
		
		//加密设置
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("md5");//加密方式
		matcher.setHashIterations(1);//加密次数
		customRealm.setCredentialsMatcher(matcher);
		
		//2.主体认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","123456");
		subject.login(token);
		System.out.println(subject.isAuthenticated());
	}
}
