package com.idap.web.repository.common.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 




import com.idap.dataprocess.definition.service.impl.ConceptModelDicServiceImpl;
import com.idap.intextservice.repository.entity.KnowledgeBase;
import com.idap.intextservice.repository.entity.KnowledgeBaseAtt;
import com.idap.intextservice.repository.service.IKnowledgeBaseAttService; 

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
public class UploadController {

	 private static final Log logger = LogFactory.getLog(UploadController.class);

	@Resource(name = "knowledgeBaseAttService")
	IKnowledgeBaseAttService knowledgeBaseAttService;

	@ResponseBody
	@RequestMapping(value = "uploadModule")
	public Map<String, String> uploadModule(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request,   String   knowledgeId ,String       knowledgeType ) {
		//null日期SPRING无法转成DATE。       
		logger.debug( "开始=======web========77999999999999999999knowledgeType["+knowledgeType );
		String path = request.getSession().getServletContext()
				.getRealPath("upload");           
		String fileName = file.getOriginalFilename();
		// String fileName = new Date().getTime()+".jpg";
		logger.debug(" ------        &&&&&&&&&&  save path : " 	+ path);
		logger.debug( "fileName : " + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();// 创建目录
		}

		Map<String, String> results = new HashMap<String, String>();

		// 保存
		try {
			logger.debug( "开始保存文件");
			file.transferTo(targetFile);

			// 存附件实体
			KnowledgeBaseAtt knowledgeAtt = new KnowledgeBaseAtt();
			//knowledgevo
			KnowledgeBaseAtt   entity = new  KnowledgeBaseAtt();
			
			entity.setKnowledgeId(    knowledgeId );//   knowledgevo.getKnowledgeId());
			entity.setFileName(fileName);
			                                  
			entity.setFileType( knowledgeType);//    knowledgevo.getKnowledgeType()
			entity.setUploadDate(new Date());
			entity.setUploadStats("uploaded");
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
