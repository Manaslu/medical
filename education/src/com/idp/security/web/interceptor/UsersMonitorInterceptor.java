package com.idp.security.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.entity.IUser;
import com.idp.security.web.listener.UsersMonitorListener;
import com.idp.security.web.utils.SecurityUtils;

/**
 * 在线人数统计拦截器
 * 
 * @author panfei
 * 
 */
public class UsersMonitorInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if ((request.getParameter("auth") != null && "true".equals(request
				.getParameter("auth")))) {
			if (SecurityUtils.isLogInSystem(request)) {
				IUser user = (IUser) request.getSession().getAttribute(
						Constants.USER_INFO);
				// 判断session重用
				Object listener = request.getSession().getAttribute(Constants.USER_INFO_LISTENER);
				if (listener != null) {
					UsersMonitorListener usrlistener = (UsersMonitorListener) listener;
					if (request.getSession().getId().equals(usrlistener.getSessionid())) {
						return;
					}
				}

				request.getSession().setAttribute(Constants.USER_INFO_LISTENER,
						new UsersMonitorListener(user, request.getSession().getId()));
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
