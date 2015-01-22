package com.idap.web.clinic.controller;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.idap.clinic.entity.UploadFile;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/uploadFile")
public class UploadFileController extends BaseController<UploadFile, String> {
	@Resource(name = "uploadFileService")
	public void setBaseService(IBaseService<UploadFile, String> baseService) {
		super.setBaseService(baseService);
	}
 
}
