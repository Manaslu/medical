package com.idap.web.repository.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.intextservice.repository.entity.LabelLib;
import com.idap.intextservice.repository.service.ILabelLibService;
import com.idp.pub.constants.Constants;

import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.institution.service.IInstitutionService;
import com.idp.sysmgr.user.entity.UserRoleRela;
import com.idp.sysmgr.user.service.IUserRoleRelaService;

@Controller
@RequestMapping(value = "/labelLib")
public class LabelLibController extends BaseController<LabelLib, String> {
	 private static final Log logger = LogFactory.getLog(ShareController.class);
		
	@Resource(name = "labelLibService")
	public void setBaseService(IBaseService<LabelLib, String> baseService) {
		super.setBaseService(baseService);
	}
	@Resource(name = "labelLibService")
	public ILabelLibService labelLibService;
 
	@Resource(name = "userRoleRelaService")
	public IUserRoleRelaService userRoleRelaService;
 

	@Resource(name = "institutionService")
	public  IInstitutionService	institutionService;

	@RequestMapping( method = RequestMethod.GET)
	@ResponseBody
	public long getRoleId (long id) {
  
		UserRoleRela userRole = this.userRoleRelaService.getById(id);
		return userRole.getRoleId();
	} 
	/*
	 * 
	 * 重写标签保存方法
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	@Override
	public Map<String, Object> create(LabelLib entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			if (entity.getAddFlag() != null
					&& entity.getAddFlag().equalsIgnoreCase("true")) {
				entity.setCreateDate(new Date());
				System.out.println(new Date());
			}
			super.getBaseService().save(entity);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	            
 
	
	@RequestMapping(params = "method=getLabelId",   method = RequestMethod.GET)
	@ResponseBody 
	public  Map<String, Object>   queryLabelId(String names) {
		String[] labelNames = null;
		if( null!= names){
			labelNames= names.split(",");
		}  
		  
		String[] labelIds = new String[labelNames.length];
		// StringBuffer idArray= new StringBuffer ();
		for (int k = 0; k < labelNames.length; k++) {
			String[] str = new String[5];
			logger.debug("  labelNames[k]  [" + labelNames[k]);
			labelIds[k] = this.labelLibService.queryLabelId(labelNames[k]);

			// idArray.append(this.labelLibService.queryLabelId(labelNames[k])+",");

		}        
		Map<String, Object> results = Constants.MAP();
		
		logger.debug("labelIds result["+labelIds);
		results.put("labelIdArray",      labelIds);
		 
		return results;

	}          
	
	@RequestMapping(params = "method=ifRepetitionName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryNameList(String  labelName) {  //labelName
	List<LabelLib>  nameList = this.labelLibService.queryNameList(labelName);  //queryNameList (S
	             
		Map<String, Object> results = Constants.MAP();
		results.put("listSize", nameList.size()  );
		return results;

	}
	
	
	/*@RequestMapping(params = "orglist=true",method = RequestMethod.GET)           
	@ResponseBody      
	public  List<Institution>  queryLowerLevelorgcd (@RequestParam("params") String    orgCd) { 
		System.out.println("----------------999999");     
		   
		//return this.institutionService.queryLowerLevelorgcd(orgCd);
		                                      
	}   */   

}
