package com.idp.workflow.itf.service.primarykey;

/**
 * 主键生成策略器
 * 
 * @author panfei
 * 
 */
public interface IPrimaryKeyCreater {

	/**
	 * 创建主键策略生成工具
	 * 
	 * @return
	 */
	Object createDbIdGenerator();
}
