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
import com.idap.clinic.entity.DoctorsManagement;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/doctorsManagement")
public class DoctorsManagementController extends BaseController<DoctorsManagement, String> {
	@Resource(name = "doctorsManagementService")
	public void setBaseService(IBaseService<DoctorsManagement, String> baseService) {
		super.setBaseService(baseService);
	}
	@RequestMapping(value="queryDoctorsFromClinic")
	@ResponseBody
	public List<DoctorsManagement> queryDoctorsManagement(@RequestParam int pageno,@RequestParam(value="uid") String clinic_id){
		Pager<DoctorsManagement> doctorResult=new Pager<DoctorsManagement>();
		doctorResult.setCurrent(pageno);
		Map<String,Object> hmap=new HashMap<String,Object>();
		hmap.put("orderBy", "doctor_date");
		hmap.put("clinic_id", clinic_id);
		Pager<DoctorsManagement> p=this.getBaseService().findByPager(doctorResult, hmap);
		return p.getData();
		
	}
	@RequestMapping(value="queryDoctorById")
	@ResponseBody
	public DoctorsManagement queryDoctorsManagement(@RequestParam String id){
		return this.getBaseService().getById(id);
		
	}
 
}
