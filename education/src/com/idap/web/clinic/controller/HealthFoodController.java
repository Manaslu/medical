package com.idap.web.clinic.controller;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.HealthFood;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/healthFood")
public class HealthFoodController extends BaseController<HealthFood, String> {
	@Resource(name = "healthFoodService")
	public void setBaseService(IBaseService<HealthFood, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
