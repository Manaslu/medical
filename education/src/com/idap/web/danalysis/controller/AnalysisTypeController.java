package com.idap.web.danalysis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import   com.idap.danalysis.entity.AnalysisType;

@Controller
@RequestMapping(value = "/analysisType")
public class AnalysisTypeController extends BaseController<AnalysisType, String> {
	@Resource(name = "analysisTypeService")
	public void setBaseService(IBaseService<AnalysisType, String> baseService) {
		super.setBaseService(baseService);
	}
	
 
}
