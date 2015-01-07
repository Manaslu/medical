package com.idap.clinic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.idap.clinic.entity.IllnessType;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2015-1-7
 * @开发人员：wang
 * @功能描述：疾病类别
 * @修改日志： ###################################################
 */

@Service("illnessTypeService")
public class IllnessTypeServiceImpl extends DefaultBaseService<IllnessType, String> {

	@Resource(name = "illnessTypeDao")
	public void setBaseDao(IBaseDao<IllnessType, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "illnessTypeDao")
	public void setPagerDao(IPagerDao<IllnessType> pagerDao) {
		super.setPagerDao(pagerDao);
	}
}
