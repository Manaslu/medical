package com.idp.workflow.spring.annotation.activiti;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.impl.metadata.activiti.SpringActivitiMetaData;
import com.idp.workflow.itf.service.primarykey.IPrimaryKeyCreater;

/**
 * 基于注解的工作流元数据实现
 * 
 * @author panfei
 * 
 */
@Service("wfMetaData")
public class CommentActivitiMetaData extends SpringActivitiMetaData {

	@Autowired
	public CommentActivitiMetaData(DataSource dataSource,
			PlatformTransactionManager transaManager)
			throws WfBusinessException {
		super(dataSource, transaManager);
		this.setServiceRegAuto(true);
	}

	@Autowired(required = false)
	public void setCustomPkCreater(IPrimaryKeyCreater pkCreater) {
		this.setPkCreater(pkCreater);
	}

}
