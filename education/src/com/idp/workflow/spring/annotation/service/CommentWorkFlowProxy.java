package com.idp.workflow.spring.annotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.service.DefaultWorkFlowProxy;
import com.idp.workflow.itf.metadata.IWorkFlowMetaData;

/**
 * 基于注解的工作流服务代理类实现
 * 
 * @author panfei
 * 
 */
@Service("iworkFlowProxy")
public class CommentWorkFlowProxy extends DefaultWorkFlowProxy {

	@Autowired
	public CommentWorkFlowProxy(IWorkFlowMetaData<?> tagretSource)
			throws WfBusinessException {
		super(tagretSource);
	}

}
