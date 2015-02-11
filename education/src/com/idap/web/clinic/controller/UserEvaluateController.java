package com.idap.web.clinic.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.UserEvaluate;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/userEvaluate")
public class UserEvaluateController extends BaseController<UserEvaluate, String> {
	
 
	@Resource(name = "userEvaluateService")
	public void setBaseService(IBaseService<UserEvaluate, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
