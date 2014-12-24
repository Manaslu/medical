package com.idap.web.repository.controller;
 
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.intextservice.repository.entity.KnowledgeBase;
import com.idap.intextservice.repository.entity.LabelLib;
import com.idap.intextservice.repository.service.IKnowledgeBaseService;
import com.idp.pub.constants.Constants;
import com.idp.pub.entity.Pager;
import com.idp.pub.generatekey.entity.GeneratedKeyHolder;
import com.idp.pub.generatekey.service.IGenerateKeyMangerService;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/share")  
public class ShareController extends BaseController<KnowledgeBase, String> {
	 private static final Log logger = LogFactory.getLog(ShareController.class);
	@Resource(name = "knowledgeBaseService")
	public void setBaseService(IBaseService<KnowledgeBase, String> baseService) {
		super.setBaseService(baseService);
	}
	 private InetAddress ServerIP = null;          
	 
	@Resource(name = "knowledgeBaseService")
	public   IKnowledgeBaseService knowledgeBaseService;

	@Resource(name = "generateKeyServcie")
	public IGenerateKeyMangerService generateKeyServcie;

    
	@RequestMapping(params = "method=getLabel", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryLabel(String id) {
		List<KnowledgeBase> labelList = knowledgeBaseService
				.queryLabelCascade(id);
		Map<String, Object> results = Constants.MAP();
		
		StringBuffer names = new StringBuffer();
		for (KnowledgeBase obj : labelList) {
			List<LabelLib> LabelLibList = obj.getColumns();

			for (LabelLib lobj : LabelLibList) {
				String name = lobj.getLabelName();
				names.append(name + ",");

			} 
		}
		
		results.put("labelNames", names );
		
		return results;
	}
	       
	@RequestMapping(params = "method=genKey", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> generateKey() {
		GeneratedKeyHolder keyHolder = generateKeyServcie.getNextGeneratedKey("know");

		Map<String, Object> results = Constants.MAP();
		results.put("uuid", keyHolder.getNextKey());
		return results;

	}
	
    @RequestMapping(params ="method=getRemoteAddr", method = RequestMethod.GET) 
	@ResponseBody 
	public  Map<String, Object> init(HttpServletRequest request, HttpServletResponse response) {
		String port;                
		port = String.valueOf(request.getServerPort());
		String[]   serverurl= request.getRequestURL().toString().split("/");
		//String serverAddr = request.getServerName() +":"+ port+ request.getContextPath();
		String serverAddr = "http://"+serverurl[2]+ request.getContextPath();
		                                                       
		Map<String, Object> results = Constants.MAP();
		results.put("serverAddr", serverAddr);       
		//request.getRemoteAddr()  //客户端的IP
		//request.getServletPath()  
		System.out.println( "   9999999 request.getRequestURL()=  ==["+ request.getRequestURL()       );
		System.out.println( " --------+serverurl[2]"+serverurl[2]);
		
		                                    
		System.out.println( " --request.getRemoteAddr()=====["+request.getRemoteAddr());  
        
		logger.debug(" 3--- request.getRequestURL()=serverurl[2]  ==["+  serverurl[2]);
		 try {
			 System.out.println( " --InetAddress--"+InetAddress.getLocalHost().getLocalHost().getHostAddress()  );
			  
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
		}         
		 
		System.out.println( "8888 serverAddr  ===[" +serverAddr     );
		
		return results;

	}
	  
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@Override
	protected Map<String, Object> update(KnowledgeBase entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			if (entity.getApprovalFlag() != null
					&& entity.getApprovalFlag().equalsIgnoreCase("true")) {

				entity.setApprovalDate(new Date());

			}
			if(null!=entity.getKnowledgeSonType()){
				entity.setKnowledgeType(entity.getKnowledgeSonType() );
			}
			
			super.getBaseService().update(entity);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	@Override
	public Map<String, Object> create(KnowledgeBase entity) {
		Map<String, Object> results = Constants.MAP();
		try {                      
			entity.setApplyDate(new Date());
			             
		        
			super.getBaseService().save(entity);
			results.put("KnowledgeId", entity.getKnowledgeId());
			logger.debug(  "   .getKnowledgeId()"    +  entity.getKnowledgeId());
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	/*
	 * 功能： 分页查询
	 * 
	 * */

	@RequestMapping(params = "paging=true", method = RequestMethod.GET)
	@ResponseBody
	@Override
	protected Pager<KnowledgeBase> findByPager(Pager<KnowledgeBase> pager,
			@RequestParam("params") String values) {
		Map	paramsMap=    	JsonUtils.toMap(values);
	//	String[]   types  =
		ArrayList classifyList  =( ArrayList) paramsMap.get("typelist");
		            
		ArrayList nonCaseList = new ArrayList();
		String    nonCaseFlag = null,  caseFlag = null ;
		
	  StringBuffer   s =  new StringBuffer();
	  Map<String, Object> originalMap = JsonUtils.toMap(values);
	   
	  if(null!= classifyList){
		  for (int k = 0 ;k<classifyList.size();k++ ){
			  
		    	if(!   ( (String)classifyList.get(k)).equalsIgnoreCase("案例") ){
		    		nonCaseList.add(classifyList.get(k))  ; 
		    		nonCaseFlag="1";
		    	} 
		    	if(  ( (String)classifyList.get(k)).equalsIgnoreCase("案例") ){
		    		 
		    		caseFlag="1";
		    	} 
		    } 
	  }   
	 
	logger.debug(  "    8999999999999 - classifyList["+classifyList); 
	logger.debug(  "    nonCaseList["+nonCaseList);
	if(null!=nonCaseList){
		originalMap.put("nonCaseList", nonCaseList);  
	} 
	if(null!=nonCaseFlag){
		originalMap.put("nonCaseFlag", nonCaseFlag);  
	}  
	if(null!=caseFlag){
		originalMap.put("caseFlag", caseFlag);  
	}      
	ArrayList<String> mergeList = new ArrayList<String>();
	 
	 if (  (null!= nonCaseFlag) &&( null!= caseFlag)  ){
		 if( nonCaseFlag.equalsIgnoreCase("1") &&   caseFlag.equalsIgnoreCase("1")  ){ //  案例和非案例都有时 
			 for (int k = 0 ;k<nonCaseList.size();k++ ){
				 mergeList.add(      (String) nonCaseList.get(k)   ); 
			 }
			 mergeList.add("需求");   
			 mergeList.add("报告");   
			 mergeList.add("脚本");   
			 mergeList.add("应用报告");  
			 originalMap.put("mergeList", mergeList);   
		}	
	 }              
	 
	     
		/* */
	  pager = this.getBaseService().findByPager(pager,originalMap);
	                                     
		//pager = this.getBaseService().findByPager(pager,	JsonUtils.toMap(values));
		return pager;
	}

	
	
	

}
