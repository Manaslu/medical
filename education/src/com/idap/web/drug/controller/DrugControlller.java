package com.idap.web.drug.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.drug.entity.Drug;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
@Controller
@RequestMapping(value="/drug")
public class DrugControlller extends BaseController<Drug, String>{

	@Override
	@Resource(name="drugService")
	public void setBaseService(IBaseService<Drug, String> baseService) {
		super.setBaseService(baseService);
	}

}
