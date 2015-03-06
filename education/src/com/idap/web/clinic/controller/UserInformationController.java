package com.idap.web.clinic.controller;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.UserInformation;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/userInformation")
public class UserInformationController extends BaseController<UserInformation, String> {
	
 
	@Resource(name = "userInformationService")
	public void setBaseService(IBaseService<UserInformation, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
