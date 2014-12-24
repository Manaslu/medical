  package com.idap.web.repository.common.controller;

import java.io.File; 
import java.io.IOException; 
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.intextservice.repository.entity.KnowledgeBaseAtt;
import com.idap.intextservice.repository.service.IKnowledgeBaseAttService;
import com.idap.intextservice.repository.service.IKnowledgeBaseService;
import com.idap.web.repository.controller.ShareController;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/commonAttachment")
public class CommonAttachmentController extends BaseController<KnowledgeBaseAtt, String> {
	
	 private static final Log logger = LogFactory.getLog(ShareController.class);
		
	@Resource(name = "knowledgeBaseAttService")
	public void setBaseService(IBaseService<KnowledgeBaseAtt, String> baseService) {
		super.setBaseService(baseService);
	}  

	
	@Resource(name = "knowledgeBaseAttService")
	public   IKnowledgeBaseAttService   knowledgeBaseAttService;
  
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	@Override
	public Map<String, Object> create(KnowledgeBaseAtt entity) {
		Map<String, Object> results = Constants.MAP();
		try {                      
			entity.setUploadDate(new Date());
			             
		           
			super.getBaseService().save(entity);
			//results.put("KnowledgeId", entity.getKnowledgeId());
			logger.debug(  "   . id========()["    +  entity.getId()  );
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	                                                                                              
	   
	    
	/**
	 * 文件下载
	 * 
	 * @param response
	 * @param request
	 * @param realName
	 * @param name
	 * @throws IOException 
	 */

	@RequestMapping(params = "method=mydownload", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> mydownload(@RequestParam String fileDir,
			HttpServletResponse response, HttpServletRequest request,
			String fileName, String name, String fileType) throws IOException {

		String filePathName = request.getSession().getServletContext()
				.getRealPath("/")	+ "upload" + File.separator + fileName;                                      
		System.out.println(fileName + "] 测试 == 999=        filePathName   ["	+ filePathName);
		// String path = this.servletContext.getRealPath("/WEB-INF/load") +
		// "\\aaa\\" + fileName;
                                 
		HttpHeaders headers = new HttpHeaders();
		//setContentType("application/octet-stream; charset=utf-8");
		
		headers.setCacheControl("no-cache");
		headers.setPragma("headers");
		headers.setExpires(-1);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		logger.debug("  MediaType.APPLICATION_OCTET_STREAM["	+ MediaType.APPLICATION_OCTET_STREAM);
		 
		if (null != fileType) {
			if (fileType.equalsIgnoreCase("jpg")) {
				headers.setContentType(MediaType.IMAGE_JPEG);
			}
			if (fileType.equalsIgnoreCase("gif")) {
				headers.setContentType(MediaType.IMAGE_GIF);
			}
			if (fileType.equalsIgnoreCase("png")) {
				headers.setContentType(MediaType.IMAGE_PNG);
			}
		}
				                                                                 	        			   					
		headers.setContentDispositionFormData("attachment",	java.net.URLEncoder.encode(fileName, "UTF-8") 	);
  
		return new ResponseEntity<byte[]>(	FileUtils.readFileToByteArray(new File(fileDir)), headers,
				HttpStatus.OK );   
		 //return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(zipFile), headers, HttpStatus.OK);
  
	}   

	 
	@RequestMapping(params = "method=verifyFile", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String, Object>   verifyFile(  String fileDir,  HttpServletRequest request) throws IOException {
	         // fileDir :  fileDir   
		                       
	        Map<String, Object> results = Constants.MAP();
			                   
	        if(null!= fileDir){  
	        	if(  new File(fileDir).exists()){
	 		    	 logger.debug("1  ex  ["  +new File(fileDir).exists() );
	 		    	 results.put("fileDownloadFlag", "yes"    );    
	 		    }   
	 		    if( ! new File(fileDir).exists()){
	 		    	 results.put("fileDownloadFlag", "no"    );      
	 		    	 logger.debug("2 no filedir    ex  ["  +new File(fileDir).exists() );
	 		    	// ResponseEntity
	 		    }     
	        } else {
	        	results.put("fileDownloadFlag", "no"    );    
	        }
		return results; 
	}
	 
	
	@RequestMapping(params = "method=download", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> download( String fileDir, String fileType,String fileId, HttpServletRequest request) throws IOException {
		           
		logger.debug("=============fileId====" + fileId);  	              
		if (null != fileDir) {
			if (!new File(fileDir).exists()) {
				logger.debug("2 no filedir    ex  [");
			}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("no-cache");
		headers.setPragma("headers");
		headers.setExpires(-1);

		//
		logger.debug("  MediaType.APPLICATION_OCTET_STREAM["
				+ MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		if (null != fileType) {
			if (fileType.equalsIgnoreCase("jpg")) {
				headers.setContentType(MediaType.IMAGE_JPEG);
			}
			if (fileType.equalsIgnoreCase("gif")) {
				headers.setContentType(MediaType.IMAGE_GIF);
			}
			if (fileType.equalsIgnoreCase("png")) {
				headers.setContentType(MediaType.IMAGE_PNG);
			}
		}
		String oldFileName="";
		Map<String, Object> params = Constants.MAP();
		params.put("id", fileId);
		List<KnowledgeBaseAtt> attachmentList = knowledgeBaseAttService.query(params);
          if(null!=attachmentList   ){
        	 oldFileName = attachmentList.get(0).getOldFileName();
      	 	 logger.debug("==oldFileName===[" + oldFileName);
          }
		
		try {
			if (null != oldFileName) { 
				logger.debug("=====GBK    iso8859-1           ===["
						+ new String(oldFileName.getBytes("GBK"),"iso8859-1") );
				
				logger.debug("=========new String(oldFileName.getBytes(GBK), utf-8  ===["
						+ new String(oldFileName.getBytes("utf-8"), "GBK"));

				logger.debug("====tttttttt66666== iso8859-1)===["
						+ new String(oldFileName.getBytes("iso-8859-1"), "GBK"));
         
                                 
				// String(logBO.getSourFileName().getBytes("GBK"),"iso8859-1")
				headers.setContentDispositionFormData("attachment", new String(
						oldFileName.getBytes("GBK"), "iso-8859-1"));
				    

			} 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
  
		return new ResponseEntity<byte[]>(
				FileUtils.readFileToByteArray(new File(fileDir)), headers,
				HttpStatus.OK);

	}   
	                           
}
