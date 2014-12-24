package com.idap.web.repository.common.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 





import  com.idap.web.repository.controller.ShareController;







import org.springframework.web.bind.annotation.RequestMethod;

import com.idap.dataprocess.definition.service.impl.ConceptModelDicServiceImpl;
import com.idap.intextservice.repository.entity.KnowledgeBase;
import com.idap.intextservice.repository.entity.KnowledgeBaseAtt;
import com.idap.intextservice.repository.entity.LabelLib;
import com.idap.intextservice.repository.service.IKnowledgeBaseAttService; 
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

import java.util.Date;  
import java.util.Map;
 







import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.HashMap; 

/**
 * 文件上传
 * 
 * @param req
 * @param file
 * @return
 * @throws BusinessException
 */
@Controller
@RequestMapping("/uploadModule")          
public class    RepositoryUploadController   extends BaseController<KnowledgeBase, String> {
                      
	 private static final Log logger = LogFactory.getLog(RepositoryUploadController.class); 
	@Resource(name = "knowledgeBaseAttService")
	IKnowledgeBaseAttService knowledgeBaseAttService;
	
	@Resource(name = "knowledgeBaseService")
	public void setBaseService(IBaseService<KnowledgeBase, String> baseService) {
		super.setBaseService(baseService);
	}
                           
	@ResponseBody                 
	@RequestMapping(method = RequestMethod.POST,params="optType=saveAll")                        
	public Map<String, String> uploadModule(
			@RequestParam(value = "file") MultipartFile file,
			HttpServletRequest request,   String   knowledgeId ,
			String		knowledgeName, String  labelName ,
			String   knowledgeType ,  String   knowledgeDesc,
			String  content  , String   approvalStats,  String applyUser ) {
		//null日期SPRING无法转成DATE。       
		logger.debug(knowledgeId+ "]knowledgeId 开始=======web========77999999999999999999knowledgeType" );
		String path = request.getSession().getServletContext().getRealPath("upload");             
		
		
		String fileName = file.getOriginalFilename();
		   path=path+knowledgeId;                   
		logger.debug(path+"====  &&&&&&&&&&  save path : " 	+ path);
		
		File targetFile = new File(path, fileName);
		logger.debug( "fileName : " + fileName+"======"+targetFile.exists());
		
		if (!targetFile.exists()) {  
			targetFile.mkdirs();// 创建目录
		}  

		Map<String, String> results = new HashMap<String, String>();
	//	ShareController  =        new ShareController();
		// 保存
		try {
			logger.debug( "开始保存文件");
			file.transferTo(targetFile); 
			       
				// 存附件实体 
				KnowledgeBaseAtt   entity = new  KnowledgeBaseAtt(); 
				entity.setKnowledgeId(    knowledgeId );//   knowledgevo.getKnowledgeId());
				entity.setFileName(fileName);
				                                  
				entity.setFileType( knowledgeType);//    knowledgevo.getKnowledgeType()
				entity.setUploadDate(new Date());
				entity.setUploadStats("已上传");
				entity.setFileDir(path);
				
		 	    this.knowledgeBaseAttService.save(entity);
				   
	 	   logger.debug( "----------保存文件           结束");
			
			results.put("success", "true");
			results.put("filePath", request.getContextPath() + "/upload/"
					+ fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	  
 
}
