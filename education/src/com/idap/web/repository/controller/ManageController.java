package com.idap.web.repository.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource; 

import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.intextservice.repository.entity.KnowledgeBase;
import com.idap.intextservice.repository.service.IKnowledgeBaseService;
import com.idap.intextservice.repository.service.IKnowledgebaseLabelService;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/manage")
public class ManageController extends BaseController<KnowledgeBase, String> {

	@Resource(name = "knowledgeBaseService")
	public void setBaseService(IBaseService<KnowledgeBase, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "knowledgeBaseService")
	public IKnowledgeBaseService knowledgeBaseService;
	                        
	@Resource(name = "knowledgebaseLabelService")
	public IKnowledgebaseLabelService knowledgebaseLabelService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFileCnt(String id) {
		KnowledgeBase knowledgeBase = this.knowledgeBaseService.getById(id);

		Map<String, Object> map = new HashMap<String, Object>();
		if(null!= knowledgeBase.getFileCnt() ){        
			map.put("filecnt", knowledgeBase.getFileCnt().intValue());
		}
		return map;
    
	}

}
