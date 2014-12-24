package com.idp.security.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.idp.pub.constants.Constants;

/**
 * 
 * 权限判断控制权限工具类
 * 
 * @author panfei
 * 
 */
public class SecurityUtils {

	/**
	 * 判断用户是否登陆
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isLogInSystem(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(Constants.USER_INFO) == null) {
			return false;
		}
		return true;
	}
}
