package com.idp.pub.generatekey.dao;

import com.idp.pub.generatekey.entity.GeneratedKeyHolder;
import com.idp.pub.generatekey.entity.KeyCondition;

/**
 * 主键生成策略接口
 * 
 * @author panfei
 * 
 */
public interface IGenerateKeyDaoStrategy {

	/**
	 * 生成主键策略方法
	 * 
	 * @param params
	 * @return
	 */
	GeneratedKeyHolder createNextGenerateKey(KeyCondition params);
}
