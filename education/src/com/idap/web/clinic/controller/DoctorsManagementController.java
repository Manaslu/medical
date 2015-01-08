package com.idap.web.clinic.controller;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.DoctorsManagement;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/doctorsManagement")
public class DoctorsManagementController extends BaseController<DoctorsManagement, String> {
	@Resource(name = "doctorsManagementService")
	public void setBaseService(IBaseService<DoctorsManagement, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
