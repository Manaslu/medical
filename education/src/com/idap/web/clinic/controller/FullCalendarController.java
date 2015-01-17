package com.idap.web.clinic.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.FullCalendar;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/fullCalendar")
public class FullCalendarController extends BaseController<FullCalendar, String> {
	
 
	@Resource(name = "fullCalendarService")
	public void setBaseService(IBaseService<FullCalendar, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
