package com.idp.pub.generatekey.dao;

import com.idp.pub.generatekey.entity.GeneratedKeyHolder;
import com.idp.pub.generatekey.entity.KeyCondition;
import com.idp.pub.utils.UUIDUtils;

/**
 * UUID主键默认生成策略 直接根据jdk的api的生成
 * 
 * @author panfei
 * 
 */
public class UuIdGenerateKeyMangerDao implements IGenerateKeyDaoStrategy {

	@Override
	public GeneratedKeyHolder createNextGenerateKey(KeyCondition params) {
		String keyStr = UUIDUtils.getDefPk();
		GeneratedKeyHolder retObjGenKeyHolder = new GeneratedKeyHolder();
		retObjGenKeyHolder.setEntityName(params.getEntityName());
		retObjGenKeyHolder.setNextKey(keyStr);
		return retObjGenKeyHolder;
	}

}
