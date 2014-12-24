package com.idap.web.danalysis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.AnalysisTheme;

@Controller
@RequestMapping(value = "/analysisTheme")
public class AnalysisThemeController extends BaseController<AnalysisTheme, String> {
	@Resource(name = "analysisThemeService")
	public void setBaseService(IBaseService<AnalysisTheme, String> baseService) {
		super.setBaseService(baseService);
	}
	
 
}
