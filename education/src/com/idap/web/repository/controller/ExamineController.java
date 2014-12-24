package com.idap.web.repository.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
 

import com.idap.intextservice.repository.entity.KnowledgeBase;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

	import java.util.Date;
import java.text.SimpleDateFormat;
            
@Controller
@RequestMapping(value = "/examine")
public class  ExamineController extends BaseController<KnowledgeBase, String> {
                         
                                      
	@Resource(name = "knowledgeBaseService")
	public void setBaseService(IBaseService<KnowledgeBase, String> baseService) {
		super.setBaseService(baseService);
	}
	 
	
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@Override
	protected Map<String, Object> update(KnowledgeBase entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			 if(entity.getApprovalFlag()!=null && entity.getApprovalFlag().equalsIgnoreCase("true")  ){
				  // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				  // System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
				 entity.setApprovalDate(   new Date()); 
				 System.out.println(  new Date());
			 }
			super.getBaseService().update(entity)   ;       //  this.baseService.update(entity);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
}
