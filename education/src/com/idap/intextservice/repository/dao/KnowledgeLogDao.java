package com.idap.intextservice.repository.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.repository.entity.KnowledgeLog;
import com.idp.pub.dao.impl.DefaultBaseDao;


/**
* @###################################################
* @功能描述:知识库维护日志Dao
* @创建日期:2014-4-21 14:39:43
* @开发人员：bai
* @修改日志：
* @###################################################
*/
@Repository("KnowledgeLogDao")
public class KnowledgeLogDao  extends DefaultBaseDao<KnowledgeLog,String>{
	public static final String NAME_SPACE="com.idap.intextservice.repository.entity.KnowledgeLog";
	 
	@Override
	public String getNamespace() {
		return NAME_SPACE;
	}
	


}


