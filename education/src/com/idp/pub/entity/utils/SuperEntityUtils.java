package com.idp.pub.entity.utils;

import com.idp.pub.entity.BaseEntity;
import com.idp.pub.entity.SuperEntity;

/**
 * SuperVO工具类
 * 
 * @author panfei
 * 
 */
public class SuperEntityUtils {

	public static BaseEntity[] convertBaseExtVO(SuperEntity... lockvos) {
		BaseEntity[] retarrs = new BaseEntity[lockvos.length];
		for (int i = 0; i < lockvos.length; i++) {
			retarrs[i] = (BaseEntity) lockvos[i];
		}
		return retarrs;
	}

}
