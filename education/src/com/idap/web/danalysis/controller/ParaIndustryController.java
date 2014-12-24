package com.idap.web.danalysis.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.ParaIndustry;

@Controller
@RequestMapping(value = "/paraIndustry")
public class ParaIndustryController extends BaseController<ParaIndustry, String> {
	@Resource(name = "paraIndustryService")
	public void setBaseService(IBaseService<ParaIndustry, String> baseService) {
		super.setBaseService(baseService);
	}
	
 
}
