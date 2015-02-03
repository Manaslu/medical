package com.idap.web.sysmgr.user.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.utils.MD5Utils;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.user.entity.User;
import com.idp.sysmgr.user.service.IUserService;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController<User, Long> {

	@Resource(name = "userService")
	public void setBaseService(IBaseService<User, Long> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "userService")
	private IUserService userService;

	//修改密码，不能用公共的
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> update(User entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			//密码加密
//			if(!"".equals(entity.getPassword())){
//				entity.setPassword(MD5Utils.md5(entity.getPassword()));
//				entity.setPassword( entity.getPassword());
//			}
			this.getBaseService().update(entity);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
	//处理密码加密的，不能用公共的
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	protected Map<String, Object> create(User entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			
			//密码加密
//			if(!"".equals(entity.getPassword())){
//				entity.setPassword(MD5Utils.md5(entity.getPassword()));
//				entity.setPassword( entity.getPassword());
//			}
			entity.setId((Long)entity.getId());
			this.getBaseService().save(entity);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
	@RequestMapping(params = "isArrays=true", method = RequestMethod.GET)
	@ResponseBody
	protected List<User> findByLists(@RequestParam("params") String values) {
		return this.getBaseService().findList(JsonUtils.toMap(values));
	}
	
	@RequestMapping(params = "pwd=true", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatepwd(String userId, String oldPwd,
			String newPwd, HttpServletRequest request) {
		Map<String, Object> results = Constants.MAP();
		try {
			this.userService.updatePwd(userId, oldPwd, newPwd);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	//查询技术联系人
	@RequestMapping(params = "queryTechContact=true", method = RequestMethod.GET)
	@ResponseBody
	protected List<User> findByList(@RequestParam("params") String values) {
		return this.userService.getTechContactList(JsonUtils.toMap(values));
	}

}
