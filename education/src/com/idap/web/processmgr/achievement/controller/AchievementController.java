package com.idap.web.processmgr.achievement.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.processmgr.special.entity.Achievement;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/achievement")
public class AchievementController extends BaseController<Achievement, String>{

	@Resource(name = "achievementService")
	public void setBaseService(IBaseService<Achievement, String> baseService) {
		super.setBaseService(baseService);
	}
	
}
