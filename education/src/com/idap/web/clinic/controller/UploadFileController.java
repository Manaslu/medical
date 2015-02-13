package com.idap.web.clinic.controller;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.clinic.entity.UploadFile;
import com.idap.clinic.service.IUploadFile;
import com.idap.web.common.controller.Commons;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
 

@Controller
@RequestMapping(value = "/uploadFile")
public class UploadFileController extends BaseController<UploadFile, String> {
	@Resource(name = "uploadFileService")
	public void setBaseService(IBaseService<UploadFile, String> baseService) {
		super.setBaseService(baseService);
	}
	
	@Resource(name = "commons")
	private Commons commons;
	
	@Resource(name = "uploadFileService")
	public   IUploadFile   uploadFileService;
	
	@RequestMapping(params = "method=download", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> download( String fileId, HttpServletRequest request) throws IOException {
		           
      
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("no-cache");
		headers.setPragma("headers");
		headers.setExpires(-1);
		
		String oldFileName="";
		String fileType="";
		String filePath="";
		Map<String, Object> params = Constants.MAP();
		if(fileId.equals("blank")){
		  String blankPic= commons.getFileUploadPath()+"/blank.jpg";
      	  headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      	  headers.setContentType(MediaType.IMAGE_JPEG);
      	  return new ResponseEntity<byte[]>(
    				FileUtils.readFileToByteArray(new File(blankPic)), headers,HttpStatus.OK);
		}
		params.put("id", fileId);
		List<UploadFile> attachmentList = uploadFileService.query(params);
          if(0!=attachmentList.size() ){
        	 oldFileName = attachmentList.get(0).getOrgFileName();
        	 fileType = attachmentList.get(0).getFileType();
        	 filePath = attachmentList.get(0).getFilePath();
          }else{
        	  String blankPic= commons.getFileUploadPath()+"/blank.jpg";
        	  headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        	  headers.setContentType(MediaType.IMAGE_JPEG);
        	  return new ResponseEntity<byte[]>(
      				FileUtils.readFileToByteArray(new File(blankPic)), headers,HttpStatus.OK);
        	  
          }
		
 
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
			if (fileType.equalsIgnoreCase("jpeg")) {
				headers.setContentType(MediaType.IMAGE_JPEG);
			}
		}
		try {
			if (null != oldFileName) { 
				headers.setContentDispositionFormData("attachment", new String(
						oldFileName.getBytes("GBK"), "iso-8859-1"));
			} 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
  
		return new ResponseEntity<byte[]>(
				FileUtils.readFileToByteArray(new File(filePath)), headers,HttpStatus.OK);

	}  
 
}
