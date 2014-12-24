package com.idap.web.sysmgr.usermonitor.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idp.pub.entity.Pager;
import com.idp.pub.utils.JsonUtils;
import com.idp.security.usersmonitor.entity.UserEntity;
import com.idp.security.usersmonitor.service.IUserMonitorQueryService;

@Controller
@RequestMapping(value = "/userMonitor")
public class UserMonitorController {

	@Resource(name = "userMonitorQueryService")
	private IUserMonitorQueryService userMonitorQueryService;

	@RequestMapping(params = "paging=true", method = RequestMethod.GET)
	@ResponseBody
	public Pager<UserEntity> findByPager(Pager<UserEntity> pager,
			@RequestParam("params") String values) {
		pager = this.userMonitorQueryService.queryUnRepeatUsers(pager,
				JsonUtils.toMap(values));
		return pager;
	}
}
