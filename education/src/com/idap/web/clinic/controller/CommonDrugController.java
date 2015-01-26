package com.idap.web.clinic.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.clinic.entity.CommonDrug;
import com.idap.clinic.entity.CommonIllness;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/commonDrug")
public class CommonDrugController extends BaseController<CommonDrug, String> {
	
 
	@Resource(name = "commonDrugService")
	public void setBaseService(IBaseService<CommonDrug, String> baseService) {
		super.setBaseService(baseService);
	}
	@RequestMapping(value="queryCommonDrug")
	@ResponseBody
	public List<CommonDrug> queryHealthFood(@RequestParam int pageno){
		Pager<CommonDrug> pdrug=new Pager<CommonDrug>();
		pdrug.setCurrent(pageno);
		Map<String,Object> hmap=new HashMap<String,Object>();
		hmap.put("orderBy", "drug_date");
		Pager<CommonDrug> p=this.getBaseService().findByPager(pdrug, hmap);
		return p.getData();
		
	}
 
}
