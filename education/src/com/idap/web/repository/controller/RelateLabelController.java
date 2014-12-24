package com.idap.web.repository.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idap.intextservice.repository.entity.KnowledgebaseLabel;
import com.idap.intextservice.repository.service.IKnowledgebaseLabelService;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/relateLabel")
public class RelateLabelController extends
		BaseController<KnowledgebaseLabel , String> {

	@Resource(name = "knowledgebaseLabelService")
	public void setBaseService(IBaseService<KnowledgebaseLabel , String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "knowledgebaseLabelService")
	public IKnowledgebaseLabelService knowledgebaseLabelService;

}
