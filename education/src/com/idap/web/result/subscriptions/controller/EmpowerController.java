package com.idap.web.result.subscriptions.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.intextservice.result.subscriptions.entity.Content;
import com.idap.intextservice.result.subscriptions.entity.Empower;
import com.idap.intextservice.result.subscriptions.entity.SubsTail;
import com.idap.intextservice.result.subscriptions.entity.Subscribe;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.user.entity.User;

/**
 * ###################################################
 * 
 * @创建日期：2014-6-19
 * @开发人员：yao
 * @功能描述：订阅管理权限controller
 * @修改日志： ###################################################
 */

@Controller
@RequestMapping(value = "/empower")
public class EmpowerController extends BaseController<Empower, String> {

	@Resource(name = "empowerService")
	public void setBaseService(IBaseService<Empower, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "empowerService")
	public IBaseService<Empower, String> empowerService;
	
	@Resource(name = "subsTailService")
	public IBaseService<SubsTail, String> subsTailService;
	
	@Resource(name = "userService")
	public IBaseService<User, String> UserService;

	@RequestMapping(params = "list=true", method = RequestMethod.GET)
	@ResponseBody
	public List<Empower> findList(@RequestParam("params") String values) {
		List<Empower> menus = empowerService.findList(JsonUtils.toMap(values));
		return menus;
	}

	@RequestMapping(method = RequestMethod.PUT, params = "create=true")
	@ResponseBody
	public Map<String, Object> create(Empower entity,
			@RequestParam("users") String users) {
		Map<String, Object> results = Constants.MAP();
		try {
			// 先删除以前设置的权限，然后重新设置一遍
			Map<String, Object> usermap = new HashMap<String, Object>();

			// 先删除以前设置的权限，然后重新设置一遍
			Map<String, Object> rolePermissions = new HashMap<String, Object>();
			usermap.put("subscribeId", entity.getSubscribeId());
			this.empowerService.delete(usermap);

			String userIds = entity.getUserId();
			String[] userId = userIds.split(",");

			// List<User> userList = new ArrayList<User>();
			// userList = (List<User>) JsonUtils.jsonToEntity(users,
			// userList.getClass());

			// String userIds = entity.getUserId();
			// String[] userId = userIds.split(",");
			// 循环给订阅赋权限
			// for (User u : userList) {
			// entity.setUserId(u.getId().toString());
			// entity.setSubscribeType("1");
			// entity.setDepartmentId(u.getOrgCd());
			// entity.setUserName(u.getUserName());
			// this.empowerService.save(entity);
			// }
			for (int i = 0; i < userId.length; i++) {
				entity.setUserId(userId[i]);
				entity.setSubscribeType("1");
				entity.setDepartmentId("dd");
				entity.setUserName("dd");
				this.empowerService.save(entity);
			}
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
	// 订阅信息管理修改
		@RequestMapping(method = RequestMethod.POST)
		@ResponseBody
		protected Map<String, Object> update(Empower empower) {
			Map<String, Object> results = Constants.MAP();
			if(null==empower.getUseremail()||"".equals(empower.getUseremail())){
				
			}else{
				User user=new User();
				user.setId(empower.getUid());
				user.setEmail(empower.getUseremail());
				UserService.update(user);
			}
			try {
				this.empowerService.update(empower);
				SubsTail subsTail=new SubsTail();
				subsTail.setReportName(empower.getReportName());
				subsTail.setSubscribeName(empower.getSubscribeName());
				subsTail.setSubscribeType(empower.getSubscribeMethod());
				subsTail.setSubscribeState(empower.getSubscribeType());
				subsTail.setUserName(empower.getUserName());
				this.subsTailService.save(subsTail);
				results.put(Constants.SUCCESS, Constants.TRUE);
			} catch (Exception e) {
				results.put(Constants.SUCCESS, Constants.FALSE);
				results.put(Constants.MESSAGE, e.getMessage());
			}
			return results;
		}
}
