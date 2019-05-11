package com.dreamtime.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class AdminInterceptor implements Interceptor {

	
	public void intercept(Invocation inv) {
		Controller c = inv.getController();
		String cookiename = c.getCookie("cname");
		Object session = c.getSessionAttr("user"); 
		if(cookiename != null && session != null) {
			inv.invoke();
		}
		else {
			c.removeCookie("cname");
			c.removeSessionAttr("user");
			c.render("login/login.html");
		}
		
	}
	
}
