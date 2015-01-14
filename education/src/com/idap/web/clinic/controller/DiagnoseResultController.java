package com.idap.web.clinic.controller;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.DiagnoseResult;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/diagnoseResult")
public class DiagnoseResultController extends BaseController<DiagnoseResult, String> {
	
 
	@Resource(name = "diagnoseResultService")
	public void setBaseService(IBaseService<DiagnoseResult, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
