package com.idap.intextservice.repository.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.repository.entity.KnowledgeBaseAtt;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述：知识库附件Dao
 * @创建日期：2014-4-23 11:12:35
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */
@Repository("KnowledgeBaseAttDao")
public class KnowledgeBaseAttDao extends
		DefaultBaseDao<KnowledgeBaseAtt, String> {
	public static final String NAME_SPACE = "com.idap.intextservice.repository.entity.KnowledgeBaseAtt";

	@Override
	public String getNamespace() {
		return NAME_SPACE;
	}

}
