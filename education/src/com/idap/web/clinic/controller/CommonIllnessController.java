package com.idap.web.clinic.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.clinic.entity.CommonIllness;
import com.idap.clinic.entity.HealthFood;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/commonIllness")
public class CommonIllnessController extends BaseController<CommonIllness, String> {
	@Resource(name = "commonIllnessService")
	public void setBaseService(IBaseService<CommonIllness, String> baseService) {
		super.setBaseService(baseService);
	}
	/**
	 * 手机端服务
	 * @param pageno
	 * @return
	 */
	@RequestMapping(value="queryCommonIllness")
	@ResponseBody
	public List<CommonIllness> queryIllness(@RequestParam int pageno){
		Pager<CommonIllness> pillness=new Pager<CommonIllness>();
		pillness.setCurrent(pageno);
		Map<String,Object> hmap=new HashMap<String,Object>();
		hmap.put("orderBy", "disease_date");
		Pager<CommonIllness> p=this.getBaseService().findByPager(pillness, hmap);
		return p.getData();
		
	}
	/**
	 * 手机端服务
	 * @param id
	 * @return
	 */
	@RequestMapping(value="queryIllnessById")
	@ResponseBody
	public CommonIllness queryCommonIllnessById(String id){
		return this.getBaseService().getById(id);
	}
	
 
}
