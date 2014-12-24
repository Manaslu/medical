package com.idap.web.result.subscriptions.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.intextservice.result.subscriptions.entity.Theme;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/theme")
public class ThemeController extends BaseController<Theme, String> {
	@Resource(name = "themeService")
	public void setBaseService(IBaseService<Theme, String> baseService) {
		super.setBaseService(baseService);
	}

}
