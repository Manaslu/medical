package com.idap.web.clinic.controller;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.clinic.entity.DiagnoseResult;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/diagnoseResult")
public class DiagnoseResultController extends BaseController<DiagnoseResult, String> {
	
 
	@Resource(name = "diagnoseResultService")
	public void setBaseService(IBaseService<DiagnoseResult, String> baseService) {
		super.setBaseService(baseService);
	}
	
	@RequestMapping(value="queryDiagnoseResult")
	@ResponseBody
	public List<DiagnoseResult> queryDiagnoseResult(@RequestParam int pageno,@RequestParam(value="uid") String m_user_id){
		Pager<DiagnoseResult> diagnoseResult=new Pager<DiagnoseResult>();
		diagnoseResult.setCurrent(pageno);
		Map<String,Object> hmap=new HashMap<String,Object>();
		hmap.put("orderBy", "see_doctor_date");
		hmap.put("m_user_id", m_user_id);
		Pager<DiagnoseResult> p=this.getBaseService().findByPager(diagnoseResult, hmap);
		return p.getData();
		
	}
	@RequestMapping(value="queryDiagnoseResultById")
	@ResponseBody
	public DiagnoseResult queryDiagnoseResult(@RequestParam String id){
		return this.getBaseService().getById(id);
		
	}
 
}
