package com.demo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.jdbc.pool.DataSource;

public class JdbcReamTest {
	private static DataSource dataSource = new DataSource();
	{
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://192.168.2.129:3306/demo");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
	}
	public static void main(String[] args) {
		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		jdbcRealm.setPermissionsLookupEnabled(true);//打开权限验证
		
		//自定义sql查询验证信息 ，如果不定已则使用默然的sql
		String sql = "select password from test_user where user_name = ?";
		jdbcRealm.setAuthenticationQuery(sql);
		
		//1.构建SecurityMananger 环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(jdbcRealm);
		//2.主体认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken();
		subject.login(token);
		System.out.println(subject.isAuthenticated());
	}
}
