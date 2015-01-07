package com.idap.web.clinic.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.IllnessType;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/illnessType")
public class IllnessTypeController extends BaseController<IllnessType, String> {
	@Resource(name = "illnessTypeService")
	public void setBaseService(IBaseService<IllnessType, String> baseService) {
		super.setBaseService(baseService);
	}

}
