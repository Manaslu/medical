package com.idp.security.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.idp.pub.constants.Constants;
import com.idp.security.web.pub.IConstant;
import com.idp.security.web.utils.SecurityUtils;

/**
 * 访问身份验证拦截器
 * 
 * @author panfei
 * 
 */
public class SecurityInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (SecurityUtils.isLogInSystem(request)) {
			return true;
		} else {
			String acceptType = request.getHeader("Accept");
			if (acceptType != null && acceptType.indexOf("json") != -1) {
				response.setContentType("application/json;charset=utf-8");
				// response.setCharacterEncoding("");
				Map<String, Object> results = Constants.MAP();
				results.put(Constants.SUCCESS, Constants.FALSE);
				results.put(Constants.MESSAGE, IConstant.LOGERRO_MSG);
				results.put(Constants.ERROR_CODE, 1);
				ObjectMapper jsonmapper = new ObjectMapper();
				jsonmapper.writeValue(response.getOutputStream(), results);
				response.flushBuffer();
			} else {
				response.sendRedirect(request.getContextPath()
						+ IConstant.LOGIN_PAGE);
			}
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
