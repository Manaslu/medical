package com.idap.web.clinic.controller;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.clinic.entity.DepartmentManagement;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.user.entity.User;
 

@Controller
@RequestMapping(value = "/departmentManagement")
public class DepartmentManagementController extends BaseController<DepartmentManagement, String> {
	
 
	@Resource(name = "departmentManagementService")
	public void setBaseService(IBaseService<DepartmentManagement, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
