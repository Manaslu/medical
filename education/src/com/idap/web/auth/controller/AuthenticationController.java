package com.idap.web.auth.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.entity.IUser;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.auth.service.IAuthenticationService;
import com.idp.sysmgr.menu.entity.MenuTree;
import com.idp.sysmgr.user.entity.User;

@Controller
public class AuthenticationController extends BaseController<User, String> {

	private Log logger = LogFactory.getLog(getClass());

	@Resource(name = "authenticationService")
	private IAuthenticationService authenticationService;

	@RequestMapping(value = "/auth", params = "auth=true", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> authentication(String userName, String password,
			HttpServletRequest request) {
		Map<String, Object> results = Constants.MAP();
		try {
			final IUser user = this.authenticationService.authentication(
					userName, password);
			request.getSession().setAttribute(Constants.USER_INFO, user);
			results.put(Constants.SUCCESS, Constants.TRUE);
			results.put(Constants.USER_INFO, user);
			
			results.put(Constants.CONTEXT_PATH, request.getContextPath());
		} catch (Exception e) {
			logger.debug("登录系统发生错误:" + e.getMessage());
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

	@RequestMapping(value = "/authMenu", params = "menu=true", method = RequestMethod.GET)
	@ResponseBody
	public List<MenuTree> findUserMenu(HttpServletRequest request) {
		return this.authenticationService.findUserMenu(this.getUser(request)
				.getId().toString());
	}

	@RequestMapping(value = "/auth", params = "logout=true", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request) {
		Map<String, Object> results = Constants.MAP();
		try {
			request.getSession().removeAttribute(Constants.USER_INFO);
			request.getSession().invalidate();
			results.put(Constants.SUCCESS, Constants.TRUE);
			results.put(Constants.CONTEXT_PATH, request.getContextPath());
		} catch (Exception e) {
			logger.debug("系统退出发生错误:" + e.getMessage());
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

}
