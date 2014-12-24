package com.idp.pub.utils;

import javax.servlet.http.HttpServletRequest;


public class RequestUtils {

	/**
	 * 获取用户的访问IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");

		if (!StringUtils.isEmpty(ip) && !"unknown".equals(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isEmpty(ip) && !"unknown".equals(ip)) {
			int index = ip.indexOf(',');
			if (index != -1) {
				ip = ip.substring(0, index);
			}
		} else {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
