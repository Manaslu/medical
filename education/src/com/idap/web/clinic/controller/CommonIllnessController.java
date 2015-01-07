package com.idap.web.clinic.controller;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.CommonIllness;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/commonIllness")
public class CommonIllnessController extends BaseController<CommonIllness, String> {
	@Resource(name = "commonIllnessService")
	public void setBaseService(IBaseService<CommonIllness, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
