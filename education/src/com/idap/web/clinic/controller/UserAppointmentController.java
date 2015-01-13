package com.idap.web.clinic.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.clinic.entity.UserAppointment;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/userAppointment")
public class UserAppointmentController extends BaseController<UserAppointment, String> {
	
 
	@Resource(name = "userAppointmentService")
	public void setBaseService(IBaseService<UserAppointment, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
