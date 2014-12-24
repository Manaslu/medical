package com.idap.web.processmgr.common.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.processmgr.special.entity.NodeAnnex;
import com.idap.processmgr.special.service.impl.NodeAnnexServiceImpl;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;
@Controller
@RequestMapping(value = "/nodeAnnex")
public class NodeAnnexController  extends BaseController<NodeAnnex, String> {
	
	@Resource(name = "nodeAnnexService")
	public void setBaseService(IBaseService<NodeAnnex, String> baseService) {
		super.setBaseService(baseService);
	}
	
	@Resource(name = "nodeAnnexService")
	public NodeAnnexServiceImpl nodeAnnexService;
	
	@RequestMapping(params = "isArray=true", method = RequestMethod.GET)
	@ResponseBody
	protected List<NodeAnnex> findByList(@RequestParam("params") String values) {
		return this.getBaseService().findList(JsonUtils.toMap(values));
	}
	
	@RequestMapping(params = "method=download", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> download(@RequestParam("params") String values, HttpServletRequest request) throws IOException {
		          
		        Map<String, Object> params = JsonUtils.toMap(values);
		         
		        String fileDir = (String) params.get("fileDir");// 1
		        String oldFileName = (String) params.get("fileName");// 2
		HttpHeaders headers = new HttpHeaders();        
		headers.setCacheControl("no-cache");
		headers.setPragma("headers");
		headers.setExpires(-1);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		try {
			//headers.setContentDispositionFormData("nodeAnnex",	new String( oldFileName.getBytes("utf-8"), "utf-8") );// new String( oldFileName.getBytes("utf-8"), "utf-8") );
			headers.setContentDispositionFormData("nodeAnnex", new String(oldFileName.getBytes("GBK"), "iso-8859-1"));
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(
				FileUtils.readFileToByteArray(new File(fileDir)), headers,
				HttpStatus.OK);
	}   
}
