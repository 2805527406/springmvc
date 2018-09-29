package com.demo.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * shiro 自定义授权过滤
 * @author Administrator
 *
 */
public class RolesFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
		Subject subject = getSubject(request, response);
		 Object object  = subject.getPrincipal();
		String[] roles = (String[]) o;
		if(roles == null || roles.length == 0) {//没有权限
			return true;
		}
		for(String role:roles) {//
			if(subject.hasRole(role)) {
				return true;
			}
		}
		return true;
	}

}
