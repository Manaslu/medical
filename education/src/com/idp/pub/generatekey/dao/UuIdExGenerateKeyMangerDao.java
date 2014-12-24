package com.idp.pub.generatekey.dao;

import com.idp.pub.dao.impl.SmartBaseDao;
import com.idp.pub.generatekey.entity.GenerateKeyName;
import com.idp.pub.generatekey.entity.GeneratedKeyHolder;
import com.idp.pub.generatekey.entity.KeyCondition;

/**
 * UUID主键扩展生成策略，访问数据库逻辑
 * 
 * @author panfei
 * 
 */
public class UuIdExGenerateKeyMangerDao extends SmartBaseDao<GenerateKeyName>
		implements IGenerateKeyDaoStrategy {

	@Override
	public GeneratedKeyHolder createNextGenerateKey(KeyCondition params) {
		return null;
	}

	@Override
	public String getUserDefineNamespace() {
		return null;
	}

}
