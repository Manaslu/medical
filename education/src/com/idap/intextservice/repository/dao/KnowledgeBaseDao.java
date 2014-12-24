package com.idap.intextservice.repository.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.repository.entity.KnowledgeBase;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述:知识库管理Dao
 * @创建日期:2014-4-21 14:39:43
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */
@Repository("KnowledgeBaseDao")
public class KnowledgeBaseDao extends DefaultBaseDao<KnowledgeBase, Long> {
	public static final String NAME_SPACE = "com.idap.intextservice.repository.entity.KnowledgeBase";

	@Override
	public String getNamespace() {
		return NAME_SPACE;
	}

	// 查询关联标签
	public List<KnowledgeBase> queryLabelCascade(String id) {
		return get("queryLabelCascade", id);

	}

	public List<KnowledgeBase> get(String key, String id) {
		return this.getSqlSession().selectList(sqlKey(key), id);

	}

}
