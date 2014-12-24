package com.idp.workflow.impl.service.activiti;

import org.activiti.engine.impl.persistence.StrongUuidGenerator;

import com.idp.workflow.itf.service.primarykey.IPrimaryKeyCreater;

/**
 * 主键生成uuid策略
 * 
 * @author panfei
 * 
 */
public class UuidPrimaryKeyCreater implements IPrimaryKeyCreater {

	StrongUuidGenerator uuidcreater = new StrongUuidGenerator();

	@Override
	public Object createDbIdGenerator() {
		return uuidcreater;
	}

}
