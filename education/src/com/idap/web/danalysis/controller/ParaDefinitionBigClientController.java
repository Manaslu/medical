package com.idap.web.danalysis.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.ParaDefinitionBigClient;

@Controller
@RequestMapping(value = "/paraDefinitionBigClient")
public class ParaDefinitionBigClientController extends BaseController<ParaDefinitionBigClient, String> {
	@Resource(name = "paraDefinitionBigClientService")
	public void setBaseService(IBaseService<ParaDefinitionBigClient, String> baseService) {
		super.setBaseService(baseService);
	}
	
 
}
