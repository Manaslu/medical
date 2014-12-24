package com.idp.pub.generatekey.service;

import com.idp.pub.exception.BusinessException;
import com.idp.pub.generatekey.entity.GeneratedKeyHolder;

/**
 * 主键管理服务
 * 
 * @author panfei
 * 
 */
public interface IGenerateKeyMangerService {

	/**
	 * 根据模块编号获取下一个主键
	 * 
	 * @param entityName
	 *            可以传入为空，将取默认值 实体类型名称
	 * @return
	 * @throws BusinessException
	 */
	GeneratedKeyHolder getNextGeneratedKey(String entityName)
			throws BusinessException;
}
