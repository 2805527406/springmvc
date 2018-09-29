package com.demo.shiro.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * �Զ�����Ȩ��֤
 * @author Administrator
 *
 */
public class CustomRealm extends AuthorizingRealm{

	/**
	 * ��Ȩ��֤
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Set<String> roles = getRolesByUserName((String)principals.getPrimaryPrincipal());
		Set<String> permissions = getPermissionsSet((String)principals.getPrimaryPrincipal());
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setRoles(roles);
		simpleAuthorizationInfo.setStringPermissions(permissions);
		return simpleAuthorizationInfo;
	}

	/**
	 * �˺���֤
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//��ȡ�û���
		String userName = (String) token.getPrincipal();
		//�����ݿ��л�ȡƾ֤�����룩
		String password = getPasswordByUserName(userName);
		if(password == null) {
			return null;
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo
				(userName,password,"customRealm");
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));
		return authenticationInfo;
	}
	
	Map<String,String> userMap = new HashMap<>();
	{
		//hash ����
		Md5Hash md5Hash1 = new Md5Hash("123456","zhangsan");
		userMap.put("zhangsan",md5Hash1.toString());
		Md5Hash md5Hash2 = new Md5Hash("123456","lisi");
		userMap.put("lisi",md5Hash2.toString());
		super.setName("customRealm");
	}
	
	private String getPasswordByUserName(String userName) {
	//���Դ����ݿ��ж�ȡ����
		return userMap.get(userName);
	}
	
	private Set<String> getRolesByUserName(String userName) {
		Set<String> roleSet = new HashSet<>();
		roleSet.add("admin");
		roleSet.add("user");
		return roleSet;
	}
	
	private Set<String> getPermissionsSet(String userName){
		Set<String> permissionsSet = new HashSet<>();
		permissionsSet.add("user:delete");
		permissionsSet.add("user:update");
		return permissionsSet;
	}

}
