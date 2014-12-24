package com.idap.web.intextservice.dataServiceProcess.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.intextservice.dataServiceProcess.entity.DataServiceProcess;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/dataServiceProcess")
public class DataServiceProcessController extends BaseController<DataServiceProcess, String>{

	@Resource(name = "dataServiceProcessService")
	public void setBaseService(IBaseService<DataServiceProcess, String> baseService) {
		super.setBaseService(baseService);
	}

}
