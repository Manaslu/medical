package com.idap.web.clinic.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.clinic.entity.HealthFood;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/healthFood")
public class HealthFoodController extends BaseController<HealthFood, String> {
	@Resource(name = "healthFoodService")
	public void setBaseService(IBaseService<HealthFood, String> baseService) {
		super.setBaseService(baseService);
	}
	
	@RequestMapping(value="queryHealthFood")
	@ResponseBody
	public List<HealthFood> queryHealthFood(@RequestParam int pageno){
		Pager<HealthFood> phealthFood=new Pager<HealthFood>();
		phealthFood.setCurrent(pageno);
		Map<String,Object> hmap=new HashMap<String,Object>();
		hmap.put("orderBy", "eat_date");
		Pager<HealthFood> p=this.getBaseService().findByPager(phealthFood, hmap);
		return p.getData();
		
	}
	@RequestMapping(value="queryHealthFoodById")
	@ResponseBody
	public HealthFood queryHealthFoodById(@RequestParam String id){
		return this.getBaseService().getById(id);
		
	}
 
}
