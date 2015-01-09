package com.idap.web.clinic.controller;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.DepartmentManagement;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/departmentManagement")
public class DepartmentManagementController extends BaseController<DepartmentManagement, String> {
	@Resource(name = "departmentManagementService")
	public void setBaseService(IBaseService<DepartmentManagement, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
