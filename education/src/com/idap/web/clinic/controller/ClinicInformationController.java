package com.idap.web.clinic.controller;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.ClinicInformation;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/clinicInformation")
public class ClinicInformationController extends BaseController<ClinicInformation, String> {
	
 
	@Resource(name = "clinicInformationService")
	public void setBaseService(IBaseService<ClinicInformation, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
