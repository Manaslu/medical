package com.idap.web.clinic.controller;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.CommonDrug;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/commonDrug")
public class CommonDrugController extends BaseController<CommonDrug, String> {
	
 
	@Resource(name = "commonDrugService")
	public void setBaseService(IBaseService<CommonDrug, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
