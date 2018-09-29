package com.demo.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * �Զ�����Ȩ��֤����
 * @author Administrator
 *
 */
public class CustomRealmTest {
	public static void main(String[] args) {
		CustomRealm customRealm = new CustomRealm();
		//1.����SecurityMananger ����
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(customRealm);
		
		//��������
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("md5");//���ܷ�ʽ
		matcher.setHashIterations(1);//���ܴ���
		customRealm.setCredentialsMatcher(matcher);
		
		//2.������֤����
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","123456");
		subject.login(token);
		System.out.println(subject.isAuthenticated());
	}
}
